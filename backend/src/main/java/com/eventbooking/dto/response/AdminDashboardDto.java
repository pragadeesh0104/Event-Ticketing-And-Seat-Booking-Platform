package com.eventbooking.dto.response;

import java.math.BigDecimal;

public class AdminDashboardDto {
    private long totalUsers;
    private long totalOrganizers;
    private long totalEvents;
    private long totalVenues;
    private long totalConfirmedBookings;
    private BigDecimal totalPlatformRevenue;

    public AdminDashboardDto() {}

    public AdminDashboardDto(long totalUsers, long totalOrganizers, long totalEvents, long totalVenues, long totalConfirmedBookings, BigDecimal totalPlatformRevenue) {
        this.totalUsers = totalUsers;
        this.totalOrganizers = totalOrganizers;
        this.totalEvents = totalEvents;
        this.totalVenues = totalVenues;
        this.totalConfirmedBookings = totalConfirmedBookings;
        this.totalPlatformRevenue = totalPlatformRevenue;
    }

    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }
    public long getTotalOrganizers() { return totalOrganizers; }
    public void setTotalOrganizers(long totalOrganizers) { this.totalOrganizers = totalOrganizers; }
    public long getTotalEvents() { return totalEvents; }
    public void setTotalEvents(long totalEvents) { this.totalEvents = totalEvents; }
    public long getTotalVenues() { return totalVenues; }
    public void setTotalVenues(long totalVenues) { this.totalVenues = totalVenues; }
    public long getTotalConfirmedBookings() { return totalConfirmedBookings; }
    public void setTotalConfirmedBookings(long totalConfirmedBookings) { this.totalConfirmedBookings = totalConfirmedBookings; }
    public BigDecimal getTotalPlatformRevenue() { return totalPlatformRevenue; }
    public void setTotalPlatformRevenue(BigDecimal totalPlatformRevenue) { this.totalPlatformRevenue = totalPlatformRevenue; }
}
