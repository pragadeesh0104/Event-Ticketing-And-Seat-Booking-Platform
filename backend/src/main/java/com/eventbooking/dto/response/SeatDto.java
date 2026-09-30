package com.eventbooking.dto.response;

import com.eventbooking.enums.SeatType;
import java.math.BigDecimal;

public class SeatDto {
    private Long id;
    private Long venueId;
    private String sectionName;
    private String rowLabel;
    private String seatNumber;
    private SeatType seatType;
    private BigDecimal price;
    private String status; // AVAILABLE, HELD, BOOKED

    public SeatDto() {}

    public SeatDto(Long id, Long venueId, String sectionName, String rowLabel, String seatNumber, SeatType seatType, BigDecimal price, String status) {
        this.id = id;
        this.venueId = venueId;
        this.sectionName = sectionName;
        this.rowLabel = rowLabel;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.price = price;
        this.status = status;
    }

    public static SeatDtoBuilder builder() {
        return new SeatDtoBuilder();
    }

    public static class SeatDtoBuilder {
        private Long id;
        private Long venueId;
        private String sectionName;
        private String rowLabel;
        private String seatNumber;
        private SeatType seatType;
        private BigDecimal price;
        private String status = "AVAILABLE";

        public SeatDtoBuilder id(Long id) { this.id = id; return this; }
        public SeatDtoBuilder venueId(Long venueId) { this.venueId = venueId; return this; }
        public SeatDtoBuilder sectionName(String sectionName) { this.sectionName = sectionName; return this; }
        public SeatDtoBuilder rowLabel(String rowLabel) { this.rowLabel = rowLabel; return this; }
        public SeatDtoBuilder seatNumber(String seatNumber) { this.seatNumber = seatNumber; return this; }
        public SeatDtoBuilder seatType(SeatType seatType) { this.seatType = seatType; return this; }
        public SeatDtoBuilder price(BigDecimal price) { this.price = price; return this; }
        public SeatDtoBuilder status(String status) { this.status = status; return this; }

        public SeatDto build() {
            return new SeatDto(id, venueId, sectionName, rowLabel, seatNumber, seatType, price, status);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getVenueId() { return venueId; }
    public void setVenueId(Long venueId) { this.venueId = venueId; }
    public String getSectionName() { return sectionName; }
    public void setSectionName(String sectionName) { this.sectionName = sectionName; }
    public String getRowLabel() { return rowLabel; }
    public void setRowLabel(String rowLabel) { this.rowLabel = rowLabel; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public SeatType getSeatType() { return seatType; }
    public void setSeatType(SeatType seatType) { this.seatType = seatType; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
