package com.eventbooking.service;

import com.eventbooking.dto.response.AdminDashboardDto;
import com.eventbooking.dto.response.OrganizerDashboardDto;
import com.eventbooking.dto.response.UserDto;
import com.eventbooking.entity.User;
import com.eventbooking.enums.AccountStatus;
import com.eventbooking.enums.BookingStatus;
import com.eventbooking.enums.Role;
import com.eventbooking.exception.ResourceNotFoundException;
import com.eventbooking.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Analytics Dashboard Service for Organizers and System Administrators.
 */
@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;

    public DashboardService(UserRepository userRepository, EventRepository eventRepository, VenueRepository venueRepository, BookingRepository bookingRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional(readOnly = true)
    public OrganizerDashboardDto getOrganizerDashboard(String userEmail) {
        User organizer = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Organizer user not found: " + userEmail));

        long eventsCount = eventRepository.findByOrganizerId(organizer.getId()).size();
        BigDecimal revenue = bookingRepository.calculateOrganizerRevenue(organizer.getId(), BookingStatus.CONFIRMED);
        long bookingsCount = bookingRepository.findAll().stream()
                .filter(b -> b.getEvent().getOrganizer().getId().equals(organizer.getId()) && b.getStatus() == BookingStatus.CONFIRMED)
                .count();

        long ticketsSold = ticketRepository.findAll().stream()
                .filter(t -> t.getBooking().getEvent().getOrganizer().getId().equals(organizer.getId()))
                .count();

        return new OrganizerDashboardDto(eventsCount, ticketsSold, bookingsCount, revenue);
    }

    @Transactional(readOnly = true)
    public AdminDashboardDto getAdminDashboard() {
        long totalUsers = userRepository.count();
        long totalOrganizers = userRepository.findByRole(Role.ROLE_ORGANIZER).size();
        long totalEvents = eventRepository.count();
        long totalVenues = venueRepository.count();
        long totalConfirmedBookings = bookingRepository.findAll().stream()
                .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
                .count();
        BigDecimal totalRevenue = bookingRepository.calculateTotalRevenueByStatus(BookingStatus.CONFIRMED);

        return new AdminDashboardDto(totalUsers, totalOrganizers, totalEvents, totalVenues, totalConfirmedBookings, totalRevenue);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(u -> new UserDto(
                u.getId(), u.getFullName(), u.getEmail(), u.getPhone(), u.getRole(), u.getAccountStatus(), u.getCreatedAt()
        )).collect(Collectors.toList());
    }

    @Transactional
    public UserDto updateUserStatus(Long userId, AccountStatus status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        user.setAccountStatus(status);
        User saved = userRepository.save(user);
        return new UserDto(saved.getId(), saved.getFullName(), saved.getEmail(), saved.getPhone(), saved.getRole(), saved.getAccountStatus(), saved.getCreatedAt());
    }
}
