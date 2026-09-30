package com.eventbooking.dto.response;

import com.eventbooking.enums.EventStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventDto {
    private Long id;
    private Long organizerId;
    private String organizerName;
    private Long venueId;
    private String venueName;
    private String venueCity;
    private String title;
    private String description;
    private String category;
    private String posterUrl;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EventStatus status;
    private BigDecimal minPrice;
    private Integer availableSeats;
    private Integer totalSeats;

    public EventDto() {}

    public EventDto(Long id, Long organizerId, String organizerName, Long venueId, String venueName, String venueCity, String title, String description, String category, String posterUrl, LocalDateTime startTime, LocalDateTime endTime, EventStatus status, BigDecimal minPrice, Integer availableSeats, Integer totalSeats) {
        this.id = id;
        this.organizerId = organizerId;
        this.organizerName = organizerName;
        this.venueId = venueId;
        this.venueName = venueName;
        this.venueCity = venueCity;
        this.title = title;
        this.description = description;
        this.category = category;
        this.posterUrl = posterUrl;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.minPrice = minPrice;
        this.availableSeats = availableSeats;
        this.totalSeats = totalSeats;
    }

    public static EventDtoBuilder builder() {
        return new EventDtoBuilder();
    }

    public static class EventDtoBuilder {
        private Long id;
        private Long organizerId;
        private String organizerName;
        private Long venueId;
        private String venueName;
        private String venueCity;
        private String title;
        private String description;
        private String category;
        private String posterUrl;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private EventStatus status;
        private BigDecimal minPrice;
        private Integer availableSeats;
        private Integer totalSeats;

        public EventDtoBuilder id(Long id) { this.id = id; return this; }
        public EventDtoBuilder organizerId(Long organizerId) { this.organizerId = organizerId; return this; }
        public EventDtoBuilder organizerName(String organizerName) { this.organizerName = organizerName; return this; }
        public EventDtoBuilder venueId(Long venueId) { this.venueId = venueId; return this; }
        public EventDtoBuilder venueName(String venueName) { this.venueName = venueName; return this; }
        public EventDtoBuilder venueCity(String venueCity) { this.venueCity = venueCity; return this; }
        public EventDtoBuilder title(String title) { this.title = title; return this; }
        public EventDtoBuilder description(String description) { this.description = description; return this; }
        public EventDtoBuilder category(String category) { this.category = category; return this; }
        public EventDtoBuilder posterUrl(String posterUrl) { this.posterUrl = posterUrl; return this; }
        public EventDtoBuilder startTime(LocalDateTime startTime) { this.startTime = startTime; return this; }
        public EventDtoBuilder endTime(LocalDateTime endTime) { this.endTime = endTime; return this; }
        public EventDtoBuilder status(EventStatus status) { this.status = status; return this; }
        public EventDtoBuilder minPrice(BigDecimal minPrice) { this.minPrice = minPrice; return this; }
        public EventDtoBuilder availableSeats(Integer availableSeats) { this.availableSeats = availableSeats; return this; }
        public EventDtoBuilder totalSeats(Integer totalSeats) { this.totalSeats = totalSeats; return this; }

        public EventDto build() {
            return new EventDto(id, organizerId, organizerName, venueId, venueName, venueCity, title, description, category, posterUrl, startTime, endTime, status, minPrice, availableSeats, totalSeats);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOrganizerId() { return organizerId; }
    public void setOrganizerId(Long organizerId) { this.organizerId = organizerId; }
    public String getOrganizerName() { return organizerName; }
    public void setOrganizerName(String organizerName) { this.organizerName = organizerName; }
    public Long getVenueId() { return venueId; }
    public void setVenueId(Long venueId) { this.venueId = venueId; }
    public String getVenueName() { return venueName; }
    public void setVenueName(String venueName) { this.venueName = venueName; }
    public String getVenueCity() { return venueCity; }
    public void setVenueCity(String venueCity) { this.venueCity = venueCity; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }
    public BigDecimal getMinPrice() { return minPrice; }
    public void setMinPrice(BigDecimal minPrice) { this.minPrice = minPrice; }
    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }
    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }
}
