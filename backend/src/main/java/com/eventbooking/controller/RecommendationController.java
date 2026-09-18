package com.eventbooking.controller;

import com.eventbooking.dto.response.ApiResponse;
import com.eventbooking.dto.response.EventDto;
import com.eventbooking.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Smart Event Recommendations Endpoints (/api/v1/recommendations).
 */
@RestController
@RequestMapping("/api/v1/recommendations")
@Tag(name = "Smart Recommendations", description = "Rule-based smart event recommendation engine endpoints")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    @Operation(summary = "Get smart event recommendations based on category, city, and upcoming dates")
    public ResponseEntity<ApiResponse<List<EventDto>>> getRecommendations(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String city) {
        List<EventDto> recommendations = recommendationService.getRecommendedEvents(category, city);
        return ResponseEntity.ok(ApiResponse.success(recommendations, "Smart recommendations generated successfully"));
    }
}
