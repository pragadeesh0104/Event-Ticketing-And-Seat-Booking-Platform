package com.eventbooking.controller;

import com.eventbooking.dto.response.AdminDashboardDto;
import com.eventbooking.dto.response.ApiResponse;
import com.eventbooking.dto.response.UserDto;
import com.eventbooking.enums.AccountStatus;
import com.eventbooking.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Administrator Portal Endpoints (/api/v1/admin).
 */
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Administrator Portal", description = "System governance, user management, and platform statistics")
public class AdminController {

    private final DashboardService dashboardService;

    public AdminController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Get administrator platform dashboard metrics")
    public ResponseEntity<ApiResponse<AdminDashboardDto>> getDashboard() {
        AdminDashboardDto dto = dashboardService.getAdminDashboard();
        return ResponseEntity.ok(ApiResponse.success(dto, "Admin platform metrics retrieved"));
    }

    @GetMapping("/users")
    @Operation(summary = "Get list of all platform users")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> users = dashboardService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users, "All platform users retrieved"));
    }

    @PatchMapping("/users/{id}/status")
    @Operation(summary = "Toggle user account status (ACTIVE, INACTIVE, SUSPENDED)")
    public ResponseEntity<ApiResponse<UserDto>> updateUserStatus(
            @PathVariable Long id,
            @RequestParam AccountStatus status) {
        UserDto updated = dashboardService.updateUserStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success(updated, "User status updated to " + status));
    }
}
