package com.eventbooking.dto.response;

import java.math.BigDecimal;

public class OrganizerDashboardDto {
    private long totalPublishedEvents;
    private long totalTicketsSold;
    private long totalBookingsCount;
    private BigDecimal totalRevenue;

    public OrganizerDashboardDto() {}

    public OrganizerDashboardDto(long totalPublishedEvents, long totalTicketsSold, long totalBookingsCount, BigDecimal totalRevenue) {
        this.totalPublishedEvents = totalPublishedEvents;
        this.totalTicketsSold = totalTicketsSold;
        this.totalBookingsCount = totalBookingsCount;
        this.totalRevenue = totalRevenue;
    }

    public long getTotalPublishedEvents() { return totalPublishedEvents; }
    public void setTotalPublishedEvents(long totalPublishedEvents) { this.totalPublishedEvents = totalPublishedEvents; }
    public long getTotalTicketsSold() { return totalTicketsSold; }
    public void setTotalTicketsSold(long totalTicketsSold) { this.totalTicketsSold = totalTicketsSold; }
    public long getTotalBookingsCount() { return totalBookingsCount; }
    public void setTotalBookingsCount(long totalBookingsCount) { this.totalBookingsCount = totalBookingsCount; }
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
}
