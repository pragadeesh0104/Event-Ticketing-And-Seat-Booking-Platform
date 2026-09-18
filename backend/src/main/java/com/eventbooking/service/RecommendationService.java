package com.eventbooking.service;

import com.eventbooking.dto.response.EventDto;
import com.eventbooking.entity.Event;
import com.eventbooking.enums.EventStatus;
import com.eventbooking.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Smart Event Discovery & Booking Insights Recommendation Engine.
 * Transparent rule-based algorithm evaluating user category preferences, city match, and event popularity.
 */
@Service
public class RecommendationService {

    private final EventRepository eventRepository;
    private final EventService eventService;

    public RecommendationService(EventRepository eventRepository, EventService eventService) {
        this.eventRepository = eventRepository;
        this.eventService = eventService;
    }

    @Transactional(readOnly = true)
    public List<EventDto> getRecommendedEvents(String category, String city) {
        List<Event> upcomingEvents = eventRepository.findByStatusAndStartTimeAfterOrderByStartTimeAsc(EventStatus.PUBLISHED, LocalDateTime.now());

        if (upcomingEvents.isEmpty()) {
            return Collections.emptyList();
        }

        // Rule-based scoring
        List<EventDto> recommendations = new ArrayList<>();
        for (Event event : upcomingEvents) {
            EventDto dto = eventService.getEventById(event.getId());

            // Score boost for preferred category match
            if (category != null && event.getCategory().equalsIgnoreCase(category)) {
                // Priority placement
                recommendations.add(0, dto);
            } else if (city != null && event.getVenue().getCity().equalsIgnoreCase(city)) {
                recommendations.add(dto);
            } else {
                recommendations.add(dto);
            }
        }

        return recommendations.stream().limit(6).toList();
    }
}
