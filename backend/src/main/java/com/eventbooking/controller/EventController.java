package com.eventbooking.controller;

import com.eventbooking.dto.request.CreateEventRequest;
import com.eventbooking.dto.response.ApiResponse;
import com.eventbooking.dto.response.EventDto;
import com.eventbooking.dto.response.SeatDto;
import com.eventbooking.enums.EventStatus;
import com.eventbooking.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Event Endpoints (/api/v1/events).
 */
@RestController
@RequestMapping("/api/v1/events")
@Tag(name = "Event Management", description = "Public event discovery, search, and organizer management")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    @Operation(summary = "Browse published events with search and pagination")
    public ResponseEntity<ApiResponse<Page<EventDto>>> getEvents(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startTime") String sortBy) {

        Page<EventDto> events = eventService.getPublishedEvents(keyword, category, city,
                PageRequest.of(page, size, Sort.by(sortBy).ascending()));
        return ResponseEntity.ok(ApiResponse.success(events, "Events retrieved successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get detailed event info by ID")
    public ResponseEntity<ApiResponse<EventDto>> getEventById(@PathVariable Long id) {
        EventDto event = eventService.getEventById(id);
        return ResponseEntity.ok(ApiResponse.success(event, "Event details retrieved successfully"));
    }

    @GetMapping("/{eventId}/seats")
    @Operation(summary = "Get seat layout and live availability status for an event")
    public ResponseEntity<ApiResponse<List<SeatDto>>> getEventSeats(@PathVariable Long eventId) {
        List<SeatDto> seats = eventService.getEventSeats(eventId);
        return ResponseEntity.ok(ApiResponse.success(seats, "Seat layout and availability retrieved successfully"));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    @Operation(summary = "Create new event (Organizer/Admin)")
    public ResponseEntity<ApiResponse<EventDto>> createEvent(
            @Valid @RequestBody CreateEventRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        EventDto created = eventService.createEvent(request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Event created successfully in DRAFT status"));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    @Operation(summary = "Update event status (DRAFT, PUBLISHED, CANCELLED)")
    public ResponseEntity<ApiResponse<EventDto>> updateEventStatus(
            @PathVariable Long id,
            @RequestParam EventStatus status) {
        EventDto updated = eventService.updateEventStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success(updated, "Event status updated to " + status));
    }
}
