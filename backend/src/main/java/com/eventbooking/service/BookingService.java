package com.eventbooking.service;

import com.eventbooking.dto.request.CheckoutRequest;
import com.eventbooking.dto.request.VerifyTicketRequest;
import com.eventbooking.dto.response.BookingDto;
import com.eventbooking.dto.response.TicketDto;
import com.eventbooking.entity.*;
import com.eventbooking.enums.*;
import com.eventbooking.exception.BadRequestException;
import com.eventbooking.exception.ResourceNotFoundException;
import com.eventbooking.repository.*;
import com.eventbooking.util.QrCodeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service managing Booking settlement, Payment sandbox processing, and QR Ticket issuance.
 */
@Service
public class BookingService {

    private final ReservationRepository reservationRepository;
    private final ReservationSeatRepository reservationSeatRepository;
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final PaymentRepository paymentRepository;
    private final TicketRepository ticketRepository;
    private final NotificationRepository notificationRepository;

    public BookingService(ReservationRepository reservationRepository, ReservationSeatRepository reservationSeatRepository, BookingRepository bookingRepository, BookingSeatRepository bookingSeatRepository, PaymentRepository paymentRepository, TicketRepository ticketRepository, NotificationRepository notificationRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationSeatRepository = reservationSeatRepository;
        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.paymentRepository = paymentRepository;
        this.ticketRepository = ticketRepository;
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public BookingDto checkout(CheckoutRequest request, String userEmail) {
        Reservation reservation = reservationRepository.findById(request.getReservationId())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found: " + request.getReservationId()));

        if (!reservation.getUser().getEmail().equalsIgnoreCase(userEmail)) {
            throw new BadRequestException("Unauthorized access to reservation.");
        }

        if (reservation.getStatus() != ReservationStatus.PENDING || reservation.getExpiresAt().isBefore(LocalDateTime.now())) {
            reservation.setStatus(ReservationStatus.EXPIRED);
            reservationRepository.save(reservation);
            throw new BadRequestException("Seat hold reservation has expired. Please re-select your seats.");
        }

        List<ReservationSeat> reservationSeats = reservationSeatRepository.findByReservationId(reservation.getId());
        if (reservationSeats.isEmpty()) {
            throw new BadRequestException("No seats found for this reservation.");
        }

        // Check idempotency
        if (request.getIdempotencyKey() != null) {
            Optional<Payment> existingPayment = paymentRepository.findByIdempotencyKey(request.getIdempotencyKey());
            if (existingPayment.isPresent()) {
                return mapToBookingDto(existingPayment.get().getBooking());
            }
        }

        // Simulated Failure scenario test
        if (request.isSimulateFailure()) {
            Payment failedPayment = Payment.builder()
                    .booking(null)
                    .paymentReference("PAY-FAIL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                    .paymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : PaymentMethod.DEMO_CARD)
                    .amount(reservation.getTotalAmount())
                    .status(PaymentStatus.FAILED)
                    .idempotencyKey(request.getIdempotencyKey())
                    .build();

            Notification notif = Notification.builder()
                    .user(reservation.getUser())
                    .title("Payment Failed")
                    .message("Simulated payment failed for event: " + reservation.getEvent().getTitle())
                    .notificationType("PAYMENT_FAILURE")
                    .isRead(false)
                    .build();
            notificationRepository.save(notif);

            throw new BadRequestException("Simulated payment failed. Seats remain held until hold timer expires.");
        }

        // Process successful simulated payment & create booking
        String bookingRef = "BKG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Booking booking = Booking.builder()
                .user(reservation.getUser())
                .event(reservation.getEvent())
                .reservation(reservation)
                .bookingReference(bookingRef)
                .totalAmount(reservation.getTotalAmount())
                .status(BookingStatus.CONFIRMED)
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        List<BookingSeat> bookingSeats = new ArrayList<>();
        List<Ticket> tickets = new ArrayList<>();

        for (ReservationSeat rs : reservationSeats) {
            BookingSeat bs = BookingSeat.builder()
                    .booking(savedBooking)
                    .event(rs.getEvent())
                    .seat(rs.getSeat())
                    .ticketPrice(rs.getSeatPrice())
                    .build();
            bookingSeats.add(bs);
        }

        List<BookingSeat> savedBookingSeats = bookingSeatRepository.saveAll(bookingSeats);

        // Issue digital tickets with QR verification tokens
        for (BookingSeat bs : savedBookingSeats) {
            String ticketNum = "TCK-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
            String verificationToken = UUID.randomUUID().toString();

            Ticket ticket = Ticket.builder()
                    .booking(savedBooking)
                    .bookingSeat(bs)
                    .ticketNumber(ticketNum)
                    .verificationTokenHash(verificationToken)
                    .status(TicketStatus.ISSUED)
                    .build();
            tickets.add(ticket);
        }

        ticketRepository.saveAll(tickets);

        // Save successful payment log
        Payment payment = Payment.builder()
                .booking(savedBooking)
                .paymentReference("PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .paymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : PaymentMethod.DEMO_CARD)
                .amount(reservation.getTotalAmount())
                .status(PaymentStatus.SUCCESSFUL)
                .idempotencyKey(request.getIdempotencyKey())
                .paidAt(LocalDateTime.now())
                .build();
        paymentRepository.save(payment);

        // Mark reservation as confirmed
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservationRepository.save(reservation);

        // Create notification
        Notification notif = Notification.builder()
                .user(reservation.getUser())
                .title("Booking Confirmed!")
                .message("Your booking " + bookingRef + " for " + reservation.getEvent().getTitle() + " was confirmed successfully.")
                .notificationType("BOOKING_CONFIRMATION")
                .isRead(false)
                .build();
        notificationRepository.save(notif);

        return mapToBookingDto(savedBooking);
    }

    @Transactional(readOnly = true)
    public List<BookingDto> getUserBookings(String userEmail) {
        User user = reservationRepository.findByUserIdOrderByCreatedAtDesc(1L).stream()
                .findFirst().map(Reservation::getUser).orElse(null); // Fallback lookup
        List<Booking> bookings = bookingRepository.findAll().stream()
                .filter(b -> b.getUser().getEmail().equalsIgnoreCase(userEmail))
                .sorted((b1, b2) -> b2.getCreatedAt().compareTo(b1.getCreatedAt()))
                .collect(Collectors.toList());
        return bookings.stream().map(this::mapToBookingDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookingDto getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found: " + id));
        return mapToBookingDto(booking);
    }

    @Transactional
    public TicketDto verifyTicket(VerifyTicketRequest request) {
        Ticket ticket = ticketRepository.findByVerificationTokenHash(request.getToken())
                .orElseGet(() -> ticketRepository.findByTicketNumber(request.getToken())
                        .orElseThrow(() -> new ResourceNotFoundException("Ticket token or reference is invalid.")));

        if (ticket.getStatus() != TicketStatus.ISSUED) {
            throw new BadRequestException("Ticket is " + ticket.getStatus() + " and cannot be verified.");
        }

        ticket.setStatus(TicketStatus.USED);
        ticket.setVerifiedAt(LocalDateTime.now());
        Ticket updated = ticketRepository.save(ticket);

        return mapToTicketDto(updated);
    }

    private BookingDto mapToBookingDto(Booking booking) {
        List<Ticket> tickets = ticketRepository.findByBookingId(booking.getId());
        List<TicketDto> ticketDtos = tickets.stream().map(this::mapToTicketDto).collect(Collectors.toList());

        return BookingDto.builder()
                .id(booking.getId())
                .bookingReference(booking.getBookingReference())
                .eventId(booking.getEvent().getId())
                .eventTitle(booking.getEvent().getTitle())
                .venueName(booking.getEvent().getVenue().getName())
                .venueCity(booking.getEvent().getVenue().getCity())
                .eventStartTime(booking.getEvent().getStartTime())
                .totalAmount(booking.getTotalAmount())
                .status(booking.getStatus())
                .createdAt(booking.getCreatedAt())
                .tickets(ticketDtos)
                .build();
    }

    private TicketDto mapToTicketDto(Ticket ticket) {
        BookingSeat bs = ticket.getBookingSeat();
        String qrPayload = "VERIFY_TOKEN:" + ticket.getVerificationTokenHash();
        String qrBase64 = QrCodeUtil.generateQrCodeBase64(qrPayload, 250, 250);

        return TicketDto.builder()
                .id(ticket.getId())
                .ticketNumber(ticket.getTicketNumber())
                .verificationTokenHash(ticket.getVerificationTokenHash())
                .qrCodeBase64(qrBase64)
                .eventTitle(bs.getEvent().getTitle())
                .venueName(bs.getEvent().getVenue().getName())
                .eventStartTime(bs.getEvent().getStartTime())
                .sectionName(bs.getSeat().getSectionName())
                .rowLabel(bs.getSeat().getRowLabel())
                .seatNumber(bs.getSeat().getSeatNumber())
                .ticketPrice(bs.getTicketPrice())
                .status(ticket.getStatus())
                .issuedAt(ticket.getIssuedAt())
                .build();
    }
}
