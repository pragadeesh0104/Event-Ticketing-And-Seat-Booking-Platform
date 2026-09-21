package com.eventbooking.service;

import com.eventbooking.dto.response.EventDto;
import com.eventbooking.entity.Event;
import com.eventbooking.entity.User;
import com.eventbooking.entity.Venue;
import com.eventbooking.enums.EventStatus;
import com.eventbooking.enums.Role;
import com.eventbooking.exception.ResourceNotFoundException;
import com.eventbooking.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private EventTicketTypeRepository eventTicketTypeRepository;

    @Mock
    private ReservationSeatRepository reservationSeatRepository;

    @Mock
    private BookingSeatRepository bookingSeatRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void getEventById_Success() {
        User organizer = User.builder().id(10L).fullName("Org Name").build();
        Venue venue = Venue.builder().id(20L).name("Arena").city("New York").build();
        Event event = Event.builder()
                .id(1L)
                .organizer(organizer)
                .venue(venue)
                .title("Concert")
                .description("Desc")
                .category("Music")
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(2))
                .status(EventStatus.PUBLISHED)
                .build();

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(seatRepository.findByVenueIdOrderBySectionNameAscRowLabelAscSeatNumberAsc(20L)).thenReturn(Collections.emptyList());
        when(bookingSeatRepository.findConfirmedSeatsForEvent(eq(1L), any())).thenReturn(Collections.emptyList());
        when(reservationSeatRepository.findActiveHeldSeatsForEvent(eq(1L), any(), any())).thenReturn(Collections.emptyList());
        when(eventTicketTypeRepository.findByEventId(1L)).thenReturn(Collections.emptyList());

        EventDto dto = eventService.getEventById(1L);

        assertNotNull(dto);
        assertEquals("Concert", dto.getTitle());
        assertEquals("Arena", dto.getVenueName());
    }

    @Test
    void getEventById_NotFound_ThrowsResourceNotFoundException() {
        when(eventRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> eventService.getEventById(999L));
    }
}
