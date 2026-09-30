package com.eventbooking.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ReservationRequest {

    @NotNull(message = "Event ID is required")
    private Long eventId;

    @NotEmpty(message = "At least one seat must be selected")
    private List<Long> seatIds;

    public ReservationRequest() {}

    public ReservationRequest(Long eventId, List<Long> seatIds) {
        this.eventId = eventId;
        this.seatIds = seatIds;
    }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public List<Long> getSeatIds() { return seatIds; }
    public void setSeatIds(List<Long> seatIds) { this.seatIds = seatIds; }
}
