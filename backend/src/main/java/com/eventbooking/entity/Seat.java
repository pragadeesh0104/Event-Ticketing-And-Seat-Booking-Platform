package com.eventbooking.entity;

import com.eventbooking.enums.SeatType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Seat Entity for venue physical layout.
 */
@Entity
@Table(name = "seats", uniqueConstraints = {
    @UniqueConstraint(name = "uk_venue_section_row_seat", columnNames = {"venue_id", "section_name", "row_label", "seat_number"})
})
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @Column(name = "section_name", nullable = false, length = 50)
    private String sectionName;

    @Column(name = "row_label", nullable = false, length = 10)
    private String rowLabel;

    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type", nullable = false, length = 30)
    private SeatType seatType;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Seat() {}

    public Seat(Long id, Venue venue, String sectionName, String rowLabel, String seatNumber, SeatType seatType, LocalDateTime createdAt) {
        this.id = id;
        this.venue = venue;
        this.sectionName = sectionName;
        this.rowLabel = rowLabel;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.createdAt = createdAt;
    }

    public static SeatBuilder builder() {
        return new SeatBuilder();
    }

    public static class SeatBuilder {
        private Long id;
        private Venue venue;
        private String sectionName = "STANDARD";
        private String rowLabel;
        private String seatNumber;
        private SeatType seatType = SeatType.REGULAR;
        private LocalDateTime createdAt;

        public SeatBuilder id(Long id) { this.id = id; return this; }
        public SeatBuilder venue(Venue venue) { this.venue = venue; return this; }
        public SeatBuilder sectionName(String sectionName) { this.sectionName = sectionName; return this; }
        public SeatBuilder rowLabel(String rowLabel) { this.rowLabel = rowLabel; return this; }
        public SeatBuilder seatNumber(String seatNumber) { this.seatNumber = seatNumber; return this; }
        public SeatBuilder seatType(SeatType seatType) { this.seatType = seatType; return this; }
        public SeatBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Seat build() {
            return new Seat(id, venue, sectionName, rowLabel, seatNumber, seatType, createdAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Venue getVenue() { return venue; }
    public void setVenue(Venue venue) { this.venue = venue; }
    public String getSectionName() { return sectionName; }
    public void setSectionName(String sectionName) { this.sectionName = sectionName; }
    public String getRowLabel() { return rowLabel; }
    public void setRowLabel(String rowLabel) { this.rowLabel = rowLabel; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public SeatType getSeatType() { return seatType; }
    public void setSeatType(SeatType seatType) { this.seatType = seatType; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
