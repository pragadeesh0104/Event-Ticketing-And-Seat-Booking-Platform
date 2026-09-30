package com.eventbooking.dto.response;

import com.eventbooking.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BookingDto {
    private Long id;
    private String bookingReference;
    private Long eventId;
    private String eventTitle;
    private String venueName;
    private String venueCity;
    private LocalDateTime eventStartTime;
    private BigDecimal totalAmount;
    private BookingStatus status;
    private LocalDateTime createdAt;
    private List<TicketDto> tickets;

    public BookingDto() {}

    public BookingDto(Long id, String bookingReference, Long eventId, String eventTitle, String venueName, String venueCity, LocalDateTime eventStartTime, BigDecimal totalAmount, BookingStatus status, LocalDateTime createdAt, List<TicketDto> tickets) {
        this.id = id;
        this.bookingReference = bookingReference;
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.venueName = venueName;
        this.venueCity = venueCity;
        this.eventStartTime = eventStartTime;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.tickets = tickets;
    }

    public static BookingDtoBuilder builder() {
        return new BookingDtoBuilder();
    }

    public static class BookingDtoBuilder {
        private Long id;
        private String bookingReference;
        private Long eventId;
        private String eventTitle;
        private String venueName;
        private String venueCity;
        private LocalDateTime eventStartTime;
        private BigDecimal totalAmount;
        private BookingStatus status;
        private LocalDateTime createdAt;
        private List<TicketDto> tickets;

        public BookingDtoBuilder id(Long id) { this.id = id; return this; }
        public BookingDtoBuilder bookingReference(String bookingReference) { this.bookingReference = bookingReference; return this; }
        public BookingDtoBuilder eventId(Long eventId) { this.eventId = eventId; return this; }
        public BookingDtoBuilder eventTitle(String eventTitle) { this.eventTitle = eventTitle; return this; }
        public BookingDtoBuilder venueName(String venueName) { this.venueName = venueName; return this; }
        public BookingDtoBuilder venueCity(String venueCity) { this.venueCity = venueCity; return this; }
        public BookingDtoBuilder eventStartTime(LocalDateTime eventStartTime) { this.eventStartTime = eventStartTime; return this; }
        public BookingDtoBuilder totalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; return this; }
        public BookingDtoBuilder status(BookingStatus status) { this.status = status; return this; }
        public BookingDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public BookingDtoBuilder tickets(List<TicketDto> tickets) { this.tickets = tickets; return this; }

        public BookingDto build() {
            return new BookingDto(id, bookingReference, eventId, eventTitle, venueName, venueCity, eventStartTime, totalAmount, status, createdAt, tickets);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBookingReference() { return bookingReference; }
    public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public String getEventTitle() { return eventTitle; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }
    public String getVenueName() { return venueName; }
    public void setVenueName(String venueName) { this.venueName = venueName; }
    public String getVenueCity() { return venueCity; }
    public void setVenueCity(String venueCity) { this.venueCity = venueCity; }
    public LocalDateTime getEventStartTime() { return eventStartTime; }
    public void setEventStartTime(LocalDateTime eventStartTime) { this.eventStartTime = eventStartTime; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<TicketDto> getTickets() { return tickets; }
    public void setTickets(List<TicketDto> tickets) { this.tickets = tickets; }
}
