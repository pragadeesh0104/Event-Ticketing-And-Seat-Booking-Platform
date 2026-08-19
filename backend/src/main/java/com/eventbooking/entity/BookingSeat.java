package com.eventbooking.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * BookingSeat Entity representing confirmed seat allocations.
 */
@Entity
@Table(name = "booking_seats", uniqueConstraints = {
    @UniqueConstraint(name = "uk_event_seat_confirmed", columnNames = {"event_id", "seat_id"})
})
public class BookingSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @Column(name = "ticket_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal ticketPrice;

    public BookingSeat() {}

    public BookingSeat(Long id, Booking booking, Event event, Seat seat, BigDecimal ticketPrice) {
        this.id = id;
        this.booking = booking;
        this.event = event;
        this.seat = seat;
        this.ticketPrice = ticketPrice;
    }

    public static BookingSeatBuilder builder() {
        return new BookingSeatBuilder();
    }

    public static class BookingSeatBuilder {
        private Long id;
        private Booking booking;
        private Event event;
        private Seat seat;
        private BigDecimal ticketPrice;

        public BookingSeatBuilder id(Long id) { this.id = id; return this; }
        public BookingSeatBuilder booking(Booking booking) { this.booking = booking; return this; }
        public BookingSeatBuilder event(Event event) { this.event = event; return this; }
        public BookingSeatBuilder seat(Seat seat) { this.seat = seat; return this; }
        public BookingSeatBuilder ticketPrice(BigDecimal ticketPrice) { this.ticketPrice = ticketPrice; return this; }

        public BookingSeat build() {
            return new BookingSeat(id, booking, event, seat, ticketPrice);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
    public Seat getSeat() { return seat; }
    public void setSeat(Seat seat) { this.seat = seat; }
    public BigDecimal getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(BigDecimal ticketPrice) { this.ticketPrice = ticketPrice; }
}
