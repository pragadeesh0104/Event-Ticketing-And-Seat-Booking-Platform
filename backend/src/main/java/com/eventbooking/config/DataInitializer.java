package com.eventbooking.config;

import com.eventbooking.entity.*;
import com.eventbooking.enums.*;
import com.eventbooking.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Development Seed Data Initializer.
 * Seeds default accounts, sample venues, seats, and published events if database is empty.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final VenueRepository venueRepository;
    private final SeatRepository seatRepository;
    private final EventRepository eventRepository;
    private final EventTicketTypeRepository eventTicketTypeRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, VenueRepository venueRepository, SeatRepository seatRepository, EventRepository eventRepository, EventTicketTypeRepository eventTicketTypeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.venueRepository = venueRepository;
        this.seatRepository = seatRepository;
        this.eventRepository = eventRepository;
        this.eventTicketTypeRepository = eventTicketTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            seedDemoData();
        }
    }

    private void seedDemoData() {
        // 1. Demo Users
        User admin = User.builder()
                .fullName("System Administrator")
                .email("admin@eventify.com")
                .passwordHash(passwordEncoder.encode("admin123"))
                .phone("+1 800 555 0199")
                .role(Role.ROLE_ADMIN)
                .accountStatus(AccountStatus.ACTIVE)
                .build();
        userRepository.save(admin);

        User organizer = User.builder()
                .fullName("LiveNation Events")
                .email("organizer@eventify.com")
                .passwordHash(passwordEncoder.encode("organizer123"))
                .phone("+1 800 555 0188")
                .role(Role.ROLE_ORGANIZER)
                .accountStatus(AccountStatus.ACTIVE)
                .build();
        userRepository.save(organizer);

        User customer = User.builder()
                .fullName("John Customer")
                .email("customer@eventify.com")
                .passwordHash(passwordEncoder.encode("customer123"))
                .phone("+1 800 555 0177")
                .role(Role.ROLE_CUSTOMER)
                .accountStatus(AccountStatus.ACTIVE)
                .build();
        userRepository.save(customer);

        // 2. Demo Venues
        Venue venue1 = Venue.builder()
                .name("Grand Arena Center")
                .address("100 Broadway Avenue")
                .city("New York")
                .description("State-of-the-art concert and sports stadium with premium acoustic design.")
                .capacity(100)
                .active(true)
                .build();
        venueRepository.save(venue1);

        Venue venue2 = Venue.builder()
                .name("Tech Expo Center")
                .address("500 Innovation Way")
                .city("San Francisco")
                .description("Modern exhibition convention hall for tech summits and keynotes.")
                .capacity(80)
                .active(true)
                .build();
        venueRepository.save(venue2);

        // 3. Demo Seats for Venue 1 (Grand Arena)
        String[] rows = {"A", "B", "C", "D"};
        for (String row : rows) {
            for (int num = 1; num <= 10; num++) {
                SeatType type = row.equals("A") ? SeatType.VIP : (row.equals("B") ? SeatType.PREMIUM : SeatType.REGULAR);
                String section = row.equals("A") ? "VIP" : (row.equals("B") ? "PREMIUM" : "STANDARD");
                Seat seat = Seat.builder()
                        .venue(venue1)
                        .sectionName(section)
                        .rowLabel(row)
                        .seatNumber(String.valueOf(num))
                        .seatType(type)
                        .build();
                seatRepository.save(seat);
            }
        }

        // 4. Sample Events
        Event event1 = Event.builder()
                .organizer(organizer)
                .venue(venue1)
                .title("World Music Symphony 2026")
                .description("Experience an unforgettable night with top orchestra performers and live light shows.")
                .category("Music")
                .posterUrl("https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&w=800&q=80")
                .startTime(LocalDateTime.now().plusDays(15).withHour(19).withMinute(0))
                .endTime(LocalDateTime.now().plusDays(15).withHour(22).withMinute(0))
                .bookingOpenTime(LocalDateTime.now().minusDays(5))
                .bookingCloseTime(LocalDateTime.now().plusDays(15).withHour(18).withMinute(0))
                .status(EventStatus.PUBLISHED)
                .build();
        eventRepository.save(event1);

        Event event2 = Event.builder()
                .organizer(organizer)
                .venue(venue2)
                .title("Global AI & Software Summit")
                .description("The annual conference bringing together software architects and AI developers.")
                .category("Tech")
                .posterUrl("https://images.unsplash.com/photo-1540575467063-178a50c2df87?auto=format&fit=crop&w=800&q=80")
                .startTime(LocalDateTime.now().plusDays(25).withHour(9).withMinute(0))
                .endTime(LocalDateTime.now().plusDays(25).withHour(17).withMinute(0))
                .bookingOpenTime(LocalDateTime.now().minusDays(2))
                .bookingCloseTime(LocalDateTime.now().plusDays(25).withHour(8).withMinute(0))
                .status(EventStatus.PUBLISHED)
                .build();
        eventRepository.save(event2);

        // 5. Ticket Types for Event 1
        EventTicketType vipTicket = EventTicketType.builder()
                .event(event1)
                .sectionName("VIP")
                .ticketType("VIP Pass")
                .price(new BigDecimal("150.00"))
                .totalQuantity(10)
                .build();
        eventTicketTypeRepository.save(vipTicket);

        EventTicketType premTicket = EventTicketType.builder()
                .event(event1)
                .sectionName("PREMIUM")
                .ticketType("Premium Pass")
                .price(new BigDecimal("100.00"))
                .totalQuantity(10)
                .build();
        eventTicketTypeRepository.save(premTicket);

        EventTicketType stdTicket = EventTicketType.builder()
                .event(event1)
                .sectionName("STANDARD")
                .ticketType("Standard Pass")
                .price(new BigDecimal("50.00"))
                .totalQuantity(20)
                .build();
        eventTicketTypeRepository.save(stdTicket);
    }
}
