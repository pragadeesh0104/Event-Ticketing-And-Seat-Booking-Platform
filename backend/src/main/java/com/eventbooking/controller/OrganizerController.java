package com.eventbooking.controller;

import com.eventbooking.dto.response.ApiResponse;
import com.eventbooking.dto.response.OrganizerDashboardDto;
import com.eventbooking.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Organizer Portal Endpoints (/api/v1/organizer).
 */
@RestController
@RequestMapping("/api/v1/organizer")
@PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
@Tag(name = "Organizer Portal", description = "Organizer sales dashboard and analytics endpoints")
public class OrganizerController {

    private final DashboardService dashboardService;

    public OrganizerController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Get organizer dashboard statistics (events, tickets sold, revenue)")
    public ResponseEntity<ApiResponse<OrganizerDashboardDto>> getDashboard(@AuthenticationPrincipal UserDetails userDetails) {
        OrganizerDashboardDto dto = dashboardService.getOrganizerDashboard(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(dto, "Organizer dashboard statistics retrieved"));
    }
}
