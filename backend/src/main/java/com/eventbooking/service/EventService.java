package com.eventbooking.service;

import com.eventbooking.dto.request.CreateEventRequest;
import com.eventbooking.dto.response.EventDto;
import com.eventbooking.dto.response.SeatDto;
import com.eventbooking.entity.*;
import com.eventbooking.enums.BookingStatus;
import com.eventbooking.enums.EventStatus;
import com.eventbooking.enums.ReservationStatus;
import com.eventbooking.exception.BadRequestException;
import com.eventbooking.exception.ResourceNotFoundException;
import com.eventbooking.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service managing Event Discovery, Listing, and Seat Availability calculations.
 */
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final SeatRepository seatRepository;
    private final EventTicketTypeRepository eventTicketTypeRepository;
    private final ReservationSeatRepository reservationSeatRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, VenueRepository venueRepository, SeatRepository seatRepository, EventTicketTypeRepository eventTicketTypeRepository, ReservationSeatRepository reservationSeatRepository, BookingSeatRepository bookingSeatRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.seatRepository = seatRepository;
        this.eventTicketTypeRepository = eventTicketTypeRepository;
        this.reservationSeatRepository = reservationSeatRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Page<EventDto> getPublishedEvents(String keyword, String category, String city, Pageable pageable) {
        Page<Event> events = eventRepository.findByStatus(EventStatus.PUBLISHED, pageable);
        return events.map(this::mapToEventDto);
    }

    @Transactional(readOnly = true)
    public EventDto getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + id));
        return mapToEventDto(event);
    }

    @Transactional(readOnly = true)
    public List<SeatDto> getEventSeats(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        List<Seat> seats = seatRepository.findByVenueIdOrderBySectionNameAscRowLabelAscSeatNumberAsc(event.getVenue().getId());
        List<EventTicketType> ticketTypes = eventTicketTypeRepository.findByEventId(eventId);
        Map<String, BigDecimal> priceMap = ticketTypes.stream()
                .collect(Collectors.toMap(EventTicketType::getSectionName, EventTicketType::getPrice, (p1, p2) -> p1));

        List<ReservationSeat> activeHolds = reservationSeatRepository.findActiveHeldSeatsForEvent(eventId, ReservationStatus.PENDING, LocalDateTime.now());
        Set<Long> heldSeatIds = activeHolds.stream().map(rs -> rs.getSeat().getId()).collect(Collectors.toSet());

        List<BookingSeat> confirmedBookings = bookingSeatRepository.findConfirmedSeatsForEvent(eventId, BookingStatus.CONFIRMED);
        Set<Long> bookedSeatIds = confirmedBookings.stream().map(bs -> bs.getSeat().getId()).collect(Collectors.toSet());

        return seats.stream().map(seat -> {
            String status = "AVAILABLE";
            if (bookedSeatIds.contains(seat.getId())) {
                status = "BOOKED";
            } else if (heldSeatIds.contains(seat.getId())) {
                status = "HELD";
            }

            BigDecimal price = priceMap.getOrDefault(seat.getSectionName(), new BigDecimal("50.00"));

            return SeatDto.builder()
                    .id(seat.getId())
                    .venueId(seat.getVenue().getId())
                    .sectionName(seat.getSectionName())
                    .rowLabel(seat.getRowLabel())
                    .seatNumber(seat.getSeatNumber())
                    .seatType(seat.getSeatType())
                    .price(price)
                    .status(status)
                    .build();
        }).collect(Collectors.toList());
    }

    @Transactional
    public EventDto createEvent(CreateEventRequest request, String organizerEmail) {
        if (request.getEndTime().isBefore(request.getStartTime())) {
            throw new BadRequestException("Event end time cannot be before start time.");
        }

        User organizer = userRepository.findByEmail(organizerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Organizer user not found"));

        Venue venue = venueRepository.findById(request.getVenueId())
                .orElseThrow(() -> new ResourceNotFoundException("Venue not found with ID: " + request.getVenueId()));

        Event event = Event.builder()
                .organizer(organizer)
                .venue(venue)
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .posterUrl(request.getPosterUrl())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status(EventStatus.DRAFT)
                .build();

        Event savedEvent = eventRepository.save(event);
        return mapToEventDto(savedEvent);
    }

    @Transactional
    public EventDto updateEventStatus(Long eventId, EventStatus newStatus) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));
        event.setStatus(newStatus);
        Event updated = eventRepository.save(event);
        return mapToEventDto(updated);
    }

    private EventDto mapToEventDto(Event event) {
        List<Seat> seats = seatRepository.findByVenueIdOrderBySectionNameAscRowLabelAscSeatNumberAsc(event.getVenue().getId());
        int totalSeats = seats.size();

        List<BookingSeat> confirmed = bookingSeatRepository.findConfirmedSeatsForEvent(event.getId(), BookingStatus.CONFIRMED);
        List<ReservationSeat> held = reservationSeatRepository.findActiveHeldSeatsForEvent(event.getId(), ReservationStatus.PENDING, LocalDateTime.now());
        int unavailableCount = confirmed.size() + held.size();
        int availableSeats = Math.max(0, totalSeats - unavailableCount);

        List<EventTicketType> types = eventTicketTypeRepository.findByEventId(event.getId());
        BigDecimal minPrice = types.stream()
                .map(EventTicketType::getPrice)
                .min(BigDecimal::compareTo)
                .orElse(new BigDecimal("50.00"));

        return EventDto.builder()
                .id(event.getId())
                .organizerId(event.getOrganizer().getId())
                .organizerName(event.getOrganizer().getFullName())
                .venueId(event.getVenue().getId())
                .venueName(event.getVenue().getName())
                .venueCity(event.getVenue().getCity())
                .title(event.getTitle())
                .description(event.getDescription())
                .category(event.getCategory())
                .posterUrl(event.getPosterUrl())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .status(event.getStatus())
                .minPrice(minPrice)
                .availableSeats(availableSeats)
                .totalSeats(totalSeats)
                .build();
    }
}
