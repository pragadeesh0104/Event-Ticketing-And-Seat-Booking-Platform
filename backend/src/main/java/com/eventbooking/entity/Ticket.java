package com.eventbooking.entity;

import com.eventbooking.enums.TicketStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Ticket Entity issued per confirmed seat.
 */
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_seat_id", nullable = false, unique = true)
    private BookingSeat bookingSeat;

    @Column(name = "ticket_number", nullable = false, unique = true, length = 50)
    private String ticketNumber;

    @Column(name = "verification_token_hash", nullable = false, unique = true, length = 255)
    private String verificationTokenHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TicketStatus status;

    @CreationTimestamp
    @Column(name = "issued_at", updatable = false)
    private LocalDateTime issuedAt;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    public Ticket() {}

    public Ticket(Long id, Booking booking, BookingSeat bookingSeat, String ticketNumber, String verificationTokenHash, TicketStatus status, LocalDateTime issuedAt, LocalDateTime verifiedAt) {
        this.id = id;
        this.booking = booking;
        this.bookingSeat = bookingSeat;
        this.ticketNumber = ticketNumber;
        this.verificationTokenHash = verificationTokenHash;
        this.status = status;
        this.issuedAt = issuedAt;
        this.verifiedAt = verifiedAt;
    }

    public static TicketBuilder builder() {
        return new TicketBuilder();
    }

    public static class TicketBuilder {
        private Long id;
        private Booking booking;
        private BookingSeat bookingSeat;
        private String ticketNumber;
        private String verificationTokenHash;
        private TicketStatus status = TicketStatus.ISSUED;
        private LocalDateTime issuedAt;
        private LocalDateTime verifiedAt;

        public TicketBuilder id(Long id) { this.id = id; return this; }
        public TicketBuilder booking(Booking booking) { this.booking = booking; return this; }
        public TicketBuilder bookingSeat(BookingSeat bookingSeat) { this.bookingSeat = bookingSeat; return this; }
        public TicketBuilder ticketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; return this; }
        public TicketBuilder verificationTokenHash(String verificationTokenHash) { this.verificationTokenHash = verificationTokenHash; return this; }
        public TicketBuilder status(TicketStatus status) { this.status = status; return this; }
        public TicketBuilder issuedAt(LocalDateTime issuedAt) { this.issuedAt = issuedAt; return this; }
        public TicketBuilder verifiedAt(LocalDateTime verifiedAt) { this.verifiedAt = verifiedAt; return this; }

        public Ticket build() {
            return new Ticket(id, booking, bookingSeat, ticketNumber, verificationTokenHash, status, issuedAt, verifiedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
    public BookingSeat getBookingSeat() { return bookingSeat; }
    public void setBookingSeat(BookingSeat bookingSeat) { this.bookingSeat = bookingSeat; }
    public String getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; }
    public String getVerificationTokenHash() { return verificationTokenHash; }
    public void setVerificationTokenHash(String verificationTokenHash) { this.verificationTokenHash = verificationTokenHash; }
    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }
    public LocalDateTime getIssuedAt() { return issuedAt; }
    public void setIssuedAt(LocalDateTime issuedAt) { this.issuedAt = issuedAt; }
    public LocalDateTime getVerifiedAt() { return verifiedAt; }
    public void setVerifiedAt(LocalDateTime verifiedAt) { this.verifiedAt = verifiedAt; }
}
