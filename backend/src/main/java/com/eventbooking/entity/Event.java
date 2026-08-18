package com.eventbooking.entity;

import com.eventbooking.enums.EventStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Event Entity created by organizers.
 */
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(name = "poster_url", length = 500)
    private String posterUrl;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "booking_open_time")
    private LocalDateTime bookingOpenTime;

    @Column(name = "booking_close_time")
    private LocalDateTime bookingCloseTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EventStatus status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Event() {}

    public Event(Long id, User organizer, Venue venue, String title, String description, String category, String posterUrl, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime bookingOpenTime, LocalDateTime bookingCloseTime, EventStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.organizer = organizer;
        this.venue = venue;
        this.title = title;
        this.description = description;
        this.category = category;
        this.posterUrl = posterUrl;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookingOpenTime = bookingOpenTime;
        this.bookingCloseTime = bookingCloseTime;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static EventBuilder builder() {
        return new EventBuilder();
    }

    public static class EventBuilder {
        private Long id;
        private User organizer;
        private Venue venue;
        private String title;
        private String description;
        private String category;
        private String posterUrl;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private LocalDateTime bookingOpenTime;
        private LocalDateTime bookingCloseTime;
        private EventStatus status = EventStatus.DRAFT;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public EventBuilder id(Long id) { this.id = id; return this; }
        public EventBuilder organizer(User organizer) { this.organizer = organizer; return this; }
        public EventBuilder venue(Venue venue) { this.venue = venue; return this; }
        public EventBuilder title(String title) { this.title = title; return this; }
        public EventBuilder description(String description) { this.description = description; return this; }
        public EventBuilder category(String category) { this.category = category; return this; }
        public EventBuilder posterUrl(String posterUrl) { this.posterUrl = posterUrl; return this; }
        public EventBuilder startTime(LocalDateTime startTime) { this.startTime = startTime; return this; }
        public EventBuilder endTime(LocalDateTime endTime) { this.endTime = endTime; return this; }
        public EventBuilder bookingOpenTime(LocalDateTime bookingOpenTime) { this.bookingOpenTime = bookingOpenTime; return this; }
        public EventBuilder bookingCloseTime(LocalDateTime bookingCloseTime) { this.bookingCloseTime = bookingCloseTime; return this; }
        public EventBuilder status(EventStatus status) { this.status = status; return this; }
        public EventBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public EventBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Event build() {
            return new Event(id, organizer, venue, title, description, category, posterUrl, startTime, endTime, bookingOpenTime, bookingCloseTime, status, createdAt, updatedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getOrganizer() { return organizer; }
    public void setOrganizer(User organizer) { this.organizer = organizer; }
    public Venue getVenue() { return venue; }
    public void setVenue(Venue venue) { this.venue = venue; }
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
    public LocalDateTime getBookingOpenTime() { return bookingOpenTime; }
    public void setBookingOpenTime(LocalDateTime bookingOpenTime) { this.bookingOpenTime = bookingOpenTime; }
    public LocalDateTime getBookingCloseTime() { return bookingCloseTime; }
    public void setBookingCloseTime(LocalDateTime bookingCloseTime) { this.bookingCloseTime = bookingCloseTime; }
    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
