package com.eventbooking.dto.response;

import com.eventbooking.enums.TicketStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TicketDto {
    private Long id;
    private String ticketNumber;
    private String verificationTokenHash;
    private String qrCodeBase64;
    private String eventTitle;
    private String venueName;
    private LocalDateTime eventStartTime;
    private String sectionName;
    private String rowLabel;
    private String seatNumber;
    private BigDecimal ticketPrice;
    private TicketStatus status;
    private LocalDateTime issuedAt;

    public TicketDto() {}

    public TicketDto(Long id, String ticketNumber, String verificationTokenHash, String qrCodeBase64, String eventTitle, String venueName, LocalDateTime eventStartTime, String sectionName, String rowLabel, String seatNumber, BigDecimal ticketPrice, TicketStatus status, LocalDateTime issuedAt) {
        this.id = id;
        this.ticketNumber = ticketNumber;
        this.verificationTokenHash = verificationTokenHash;
        this.qrCodeBase64 = qrCodeBase64;
        this.eventTitle = eventTitle;
        this.venueName = venueName;
        this.eventStartTime = eventStartTime;
        this.sectionName = sectionName;
        this.rowLabel = rowLabel;
        this.seatNumber = seatNumber;
        this.ticketPrice = ticketPrice;
        this.status = status;
        this.issuedAt = issuedAt;
    }

    public static TicketDtoBuilder builder() {
        return new TicketDtoBuilder();
    }

    public static class TicketDtoBuilder {
        private Long id;
        private String ticketNumber;
        private String verificationTokenHash;
        private String qrCodeBase64;
        private String eventTitle;
        private String venueName;
        private LocalDateTime eventStartTime;
        private String sectionName;
        private String rowLabel;
        private String seatNumber;
        private BigDecimal ticketPrice;
        private TicketStatus status;
        private LocalDateTime issuedAt;

        public TicketDtoBuilder id(Long id) { this.id = id; return this; }
        public TicketDtoBuilder ticketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; return this; }
        public TicketDtoBuilder verificationTokenHash(String verificationTokenHash) { this.verificationTokenHash = verificationTokenHash; return this; }
        public TicketDtoBuilder qrCodeBase64(String qrCodeBase64) { this.qrCodeBase64 = qrCodeBase64; return this; }
        public TicketDtoBuilder eventTitle(String eventTitle) { this.eventTitle = eventTitle; return this; }
        public TicketDtoBuilder venueName(String venueName) { this.venueName = venueName; return this; }
        public TicketDtoBuilder eventStartTime(LocalDateTime eventStartTime) { this.eventStartTime = eventStartTime; return this; }
        public TicketDtoBuilder sectionName(String sectionName) { this.sectionName = sectionName; return this; }
        public TicketDtoBuilder rowLabel(String rowLabel) { this.rowLabel = rowLabel; return this; }
        public TicketDtoBuilder seatNumber(String seatNumber) { this.seatNumber = seatNumber; return this; }
        public TicketDtoBuilder ticketPrice(BigDecimal ticketPrice) { this.ticketPrice = ticketPrice; return this; }
        public TicketDtoBuilder status(TicketStatus status) { this.status = status; return this; }
        public TicketDtoBuilder issuedAt(LocalDateTime issuedAt) { this.issuedAt = issuedAt; return this; }

        public TicketDto build() {
            return new TicketDto(id, ticketNumber, verificationTokenHash, qrCodeBase64, eventTitle, venueName, eventStartTime, sectionName, rowLabel, seatNumber, ticketPrice, status, issuedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; }
    public String getVerificationTokenHash() { return verificationTokenHash; }
    public void setVerificationTokenHash(String verificationTokenHash) { this.verificationTokenHash = verificationTokenHash; }
    public String getQrCodeBase64() { return qrCodeBase64; }
    public void setQrCodeBase64(String qrCodeBase64) { this.qrCodeBase64 = qrCodeBase64; }
    public String getEventTitle() { return eventTitle; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }
    public String getVenueName() { return venueName; }
    public void setVenueName(String venueName) { this.venueName = venueName; }
    public LocalDateTime getEventStartTime() { return eventStartTime; }
    public void setEventStartTime(LocalDateTime eventStartTime) { this.eventStartTime = eventStartTime; }
    public String getSectionName() { return sectionName; }
    public void setSectionName(String sectionName) { this.sectionName = sectionName; }
    public String getRowLabel() { return rowLabel; }
    public void setRowLabel(String rowLabel) { this.rowLabel = rowLabel; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public BigDecimal getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(BigDecimal ticketPrice) { this.ticketPrice = ticketPrice; }
    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }
    public LocalDateTime getIssuedAt() { return issuedAt; }
    public void setIssuedAt(LocalDateTime issuedAt) { this.issuedAt = issuedAt; }
}
