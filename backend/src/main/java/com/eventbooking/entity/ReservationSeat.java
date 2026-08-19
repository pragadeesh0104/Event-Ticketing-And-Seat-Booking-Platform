package com.eventbooking.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * ReservationSeat Entity mapping seats held in a reservation.
 */
@Entity
@Table(name = "reservation_seats")
public class ReservationSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @Column(name = "seat_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal seatPrice;

    public ReservationSeat() {}

    public ReservationSeat(Long id, Reservation reservation, Event event, Seat seat, BigDecimal seatPrice) {
        this.id = id;
        this.reservation = reservation;
        this.event = event;
        this.seat = seat;
        this.seatPrice = seatPrice;
    }

    public static ReservationSeatBuilder builder() {
        return new ReservationSeatBuilder();
    }

    public static class ReservationSeatBuilder {
        private Long id;
        private Reservation reservation;
        private Event event;
        private Seat seat;
        private BigDecimal seatPrice;

        public ReservationSeatBuilder id(Long id) { this.id = id; return this; }
        public ReservationSeatBuilder reservation(Reservation reservation) { this.reservation = reservation; return this; }
        public ReservationSeatBuilder event(Event event) { this.event = event; return this; }
        public ReservationSeatBuilder seat(Seat seat) { this.seat = seat; return this; }
        public ReservationSeatBuilder seatPrice(BigDecimal seatPrice) { this.seatPrice = seatPrice; return this; }

        public ReservationSeat build() {
            return new ReservationSeat(id, reservation, event, seat, seatPrice);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }
    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
    public Seat getSeat() { return seat; }
    public void setSeat(Seat seat) { this.seat = seat; }
    public BigDecimal getSeatPrice() { return seatPrice; }
    public void setSeatPrice(BigDecimal seatPrice) { this.seatPrice = seatPrice; }
}
