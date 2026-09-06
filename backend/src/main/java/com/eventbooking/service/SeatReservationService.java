package com.eventbooking.service;

import com.eventbooking.dto.request.ReservationRequest;
import com.eventbooking.dto.response.ReservationResponse;
import com.eventbooking.entity.*;
import com.eventbooking.enums.BookingStatus;
import com.eventbooking.enums.EventStatus;
import com.eventbooking.enums.ReservationStatus;
import com.eventbooking.exception.BadRequestException;
import com.eventbooking.exception.ResourceNotFoundException;
import com.eventbooking.exception.SeatUnavailableException;
import com.eventbooking.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Concurrency-safe Seat Reservation Engine.
 * Manages 10-minute temporary seat holds with transactional DB isolation and conflict prevention.
 */
@Service
public class SeatReservationService {

    private final EventRepository eventRepository;
    private final SeatRepository seatRepository;
    private final EventTicketTypeRepository eventTicketTypeRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationSeatRepository reservationSeatRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final UserRepository userRepository;

    @Value("${app.reservation.hold-duration-minutes:10}")
    private int holdDurationMinutes;

    public SeatReservationService(EventRepository eventRepository, SeatRepository seatRepository, EventTicketTypeRepository eventTicketTypeRepository, ReservationRepository reservationRepository, ReservationSeatRepository reservationSeatRepository, BookingSeatRepository bookingSeatRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.seatRepository = seatRepository;
        this.eventTicketTypeRepository = eventTicketTypeRepository;
        this.reservationRepository = reservationRepository;
        this.reservationSeatRepository = reservationSeatRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ReservationResponse holdSeats(ReservationRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + request.getEventId()));

        if (event.getStatus() != EventStatus.PUBLISHED) {
            throw new BadRequestException("Event is not currently open for seat booking.");
        }

        List<Seat> requestedSeats = seatRepository.findAllById(request.getSeatIds());
        if (requestedSeats.size() != request.getSeatIds().size()) {
            throw new BadRequestException("One or more requested seat IDs do not exist.");
        }

        // Validate that all seats belong to the event venue
        for (Seat seat : requestedSeats) {
            if (!seat.getVenue().getId().equals(event.getVenue().getId())) {
                throw new BadRequestException("Seat " + seat.getSeatNumber() + " does not belong to the venue hosting this event.");
            }
        }

        LocalDateTime now = LocalDateTime.now();

        // 1. Check if any seat is already booked
        List<BookingSeat> confirmedBookings = bookingSeatRepository.findConfirmedSeatsForEvent(event.getId(), BookingStatus.CONFIRMED);
        Set<Long> confirmedSeatIds = confirmedBookings.stream().map(bs -> bs.getSeat().getId()).collect(Collectors.toSet());

        // 2. Check if any seat is currently held in an active non-expired reservation
        List<ReservationSeat> activeHolds = reservationSeatRepository.findActiveHeldSeatsForEvent(event.getId(), ReservationStatus.PENDING, now);
        Set<Long> heldSeatIds = activeHolds.stream().map(rs -> rs.getSeat().getId()).collect(Collectors.toSet());

        List<String> unavailableSeats = new ArrayList<>();
        for (Seat seat : requestedSeats) {
            if (confirmedSeatIds.contains(seat.getId()) || heldSeatIds.contains(seat.getId())) {
                unavailableSeats.add(seat.getRowLabel() + "-" + seat.getSeatNumber());
            }
        }

        if (!unavailableSeats.isEmpty()) {
            throw new SeatUnavailableException("The following selected seat(s) are no longer available: " + String.join(", ", unavailableSeats));
        }

        // Calculate total amount on backend using official event ticket prices
        List<EventTicketType> ticketTypes = eventTicketTypeRepository.findByEventId(event.getId());
        Map<String, BigDecimal> priceMap = ticketTypes.stream()
                .collect(Collectors.toMap(EventTicketType::getSectionName, EventTicketType::getPrice, (p1, p2) -> p1));

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<ReservationSeat> reservationSeats = new ArrayList<>();

        LocalDateTime expiresAt = now.plusMinutes(holdDurationMinutes);
        String refCode = "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Reservation reservation = Reservation.builder()
                .user(user)
                .event(event)
                .reservationReference(refCode)
                .status(ReservationStatus.PENDING)
                .expiresAt(expiresAt)
                .totalAmount(BigDecimal.ZERO)
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);

        for (Seat seat : requestedSeats) {
            BigDecimal seatPrice = priceMap.getOrDefault(seat.getSectionName(), new BigDecimal("50.00"));
            totalAmount = totalAmount.add(seatPrice);

            ReservationSeat rs = ReservationSeat.builder()
                    .reservation(savedReservation)
                    .event(event)
                    .seat(seat)
                    .seatPrice(seatPrice)
                    .build();
            reservationSeats.add(rs);
        }

        reservationSeatRepository.saveAll(reservationSeats);

        savedReservation.setTotalAmount(totalAmount);
        reservationRepository.save(savedReservation);

        long secondsRemaining = Duration.between(now, expiresAt).getSeconds();

        return ReservationResponse.builder()
                .id(savedReservation.getId())
                .reservationReference(savedReservation.getReservationReference())
                .eventId(event.getId())
                .eventTitle(event.getTitle())
                .seatIds(request.getSeatIds())
                .totalAmount(totalAmount)
                .status(savedReservation.getStatus())
                .expiresAt(expiresAt)
                .holdDurationSeconds(secondsRemaining)
                .build();
    }

    @Transactional(readOnly = true)
    public ReservationResponse getReservationDetails(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + id));

        List<ReservationSeat> seats = reservationSeatRepository.findByReservationId(id);
        List<Long> seatIds = seats.stream().map(rs -> rs.getSeat().getId()).collect(Collectors.toList());

        long secondsRemaining = Math.max(0, Duration.between(LocalDateTime.now(), reservation.getExpiresAt()).getSeconds());

        return ReservationResponse.builder()
                .id(reservation.getId())
                .reservationReference(reservation.getReservationReference())
                .eventId(reservation.getEvent().getId())
                .eventTitle(reservation.getEvent().getTitle())
                .seatIds(seatIds)
                .totalAmount(reservation.getTotalAmount())
                .status(reservation.getStatus())
                .expiresAt(reservation.getExpiresAt())
                .holdDurationSeconds(secondsRemaining)
                .build();
    }
}
