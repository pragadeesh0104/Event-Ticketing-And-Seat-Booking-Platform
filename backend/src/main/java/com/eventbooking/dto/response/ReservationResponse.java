package com.eventbooking.dto.response;

import com.eventbooking.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationResponse {
    private Long id;
    private String reservationReference;
    private Long eventId;
    private String eventTitle;
    private List<Long> seatIds;
    private BigDecimal totalAmount;
    private ReservationStatus status;
    private LocalDateTime expiresAt;
    private long holdDurationSeconds;

    public ReservationResponse() {}

    public ReservationResponse(Long id, String reservationReference, Long eventId, String eventTitle, List<Long> seatIds, BigDecimal totalAmount, ReservationStatus status, LocalDateTime expiresAt, long holdDurationSeconds) {
        this.id = id;
        this.reservationReference = reservationReference;
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.seatIds = seatIds;
        this.totalAmount = totalAmount;
        this.status = status;
        this.expiresAt = expiresAt;
        this.holdDurationSeconds = holdDurationSeconds;
    }

    public static ReservationResponseBuilder builder() {
        return new ReservationResponseBuilder();
    }

    public static class ReservationResponseBuilder {
        private Long id;
        private String reservationReference;
        private Long eventId;
        private String eventTitle;
        private List<Long> seatIds;
        private BigDecimal totalAmount;
        private ReservationStatus status;
        private LocalDateTime expiresAt;
        private long holdDurationSeconds;

        public ReservationResponseBuilder id(Long id) { this.id = id; return this; }
        public ReservationResponseBuilder reservationReference(String reservationReference) { this.reservationReference = reservationReference; return this; }
        public ReservationResponseBuilder eventId(Long eventId) { this.eventId = eventId; return this; }
        public ReservationResponseBuilder eventTitle(String eventTitle) { this.eventTitle = eventTitle; return this; }
        public ReservationResponseBuilder seatIds(List<Long> seatIds) { this.seatIds = seatIds; return this; }
        public ReservationResponseBuilder totalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; return this; }
        public ReservationResponseBuilder status(ReservationStatus status) { this.status = status; return this; }
        public ReservationResponseBuilder expiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; return this; }
        public ReservationResponseBuilder holdDurationSeconds(long holdDurationSeconds) { this.holdDurationSeconds = holdDurationSeconds; return this; }

        public ReservationResponse build() {
            return new ReservationResponse(id, reservationReference, eventId, eventTitle, seatIds, totalAmount, status, expiresAt, holdDurationSeconds);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getReservationReference() { return reservationReference; }
    public void setReservationReference(String reservationReference) { this.reservationReference = reservationReference; }
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public String getEventTitle() { return eventTitle; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }
    public List<Long> getSeatIds() { return seatIds; }
    public void setSeatIds(List<Long> seatIds) { this.seatIds = seatIds; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public long getHoldDurationSeconds() { return holdDurationSeconds; }
    public void setHoldDurationSeconds(long holdDurationSeconds) { this.holdDurationSeconds = holdDurationSeconds; }
}
