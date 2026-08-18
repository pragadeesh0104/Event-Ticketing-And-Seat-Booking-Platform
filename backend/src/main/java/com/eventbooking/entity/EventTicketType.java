package com.eventbooking.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * EventTicketType Entity for price tiering per event section.
 */
@Entity
@Table(name = "event_ticket_types", uniqueConstraints = {
    @UniqueConstraint(name = "uk_event_section_type", columnNames = {"event_id", "section_name", "ticket_type"})
})
public class EventTicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(name = "section_name", nullable = false, length = 50)
    private String sectionName;

    @Column(name = "ticket_type", nullable = false, length = 50)
    private String ticketType;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public EventTicketType() {}

    public EventTicketType(Long id, Event event, String sectionName, String ticketType, BigDecimal price, Integer totalQuantity, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.event = event;
        this.sectionName = sectionName;
        this.ticketType = ticketType;
        this.price = price;
        this.totalQuantity = totalQuantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static EventTicketTypeBuilder builder() {
        return new EventTicketTypeBuilder();
    }

    public static class EventTicketTypeBuilder {
        private Long id;
        private Event event;
        private String sectionName;
        private String ticketType;
        private BigDecimal price;
        private Integer totalQuantity;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public EventTicketTypeBuilder id(Long id) { this.id = id; return this; }
        public EventTicketTypeBuilder event(Event event) { this.event = event; return this; }
        public EventTicketTypeBuilder sectionName(String sectionName) { this.sectionName = sectionName; return this; }
        public EventTicketTypeBuilder ticketType(String ticketType) { this.ticketType = ticketType; return this; }
        public EventTicketTypeBuilder price(BigDecimal price) { this.price = price; return this; }
        public EventTicketTypeBuilder totalQuantity(Integer totalQuantity) { this.totalQuantity = totalQuantity; return this; }
        public EventTicketTypeBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public EventTicketTypeBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public EventTicketType build() {
            return new EventTicketType(id, event, sectionName, ticketType, price, totalQuantity, createdAt, updatedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }
    public String getSectionName() { return sectionName; }
    public void setSectionName(String sectionName) { this.sectionName = sectionName; }
    public String getTicketType() { return ticketType; }
    public void setTicketType(String ticketType) { this.ticketType = ticketType; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(Integer totalQuantity) { this.totalQuantity = totalQuantity; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
