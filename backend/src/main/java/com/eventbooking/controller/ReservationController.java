package com.eventbooking.controller;

import com.eventbooking.dto.request.ReservationRequest;
import com.eventbooking.dto.response.ApiResponse;
import com.eventbooking.dto.response.ReservationResponse;
import com.eventbooking.service.SeatReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Seat Reservation Endpoints (/api/v1/reservations).
 */
@RestController
@RequestMapping("/api/v1/reservations")
@Tag(name = "Seat Reservation", description = "Temporary seat hold reservation endpoints")
public class ReservationController {

    private final SeatReservationService reservationService;

    public ReservationController(SeatReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @Operation(summary = "Hold selected seats temporarily for 10 minutes")
    public ResponseEntity<ApiResponse<ReservationResponse>> holdSeats(
            @Valid @RequestBody ReservationRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        ReservationResponse response = reservationService.holdSeats(request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Seats reserved temporarily for 10 minutes"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get reservation details and hold expiration countdown")
    public ResponseEntity<ApiResponse<ReservationResponse>> getReservation(@PathVariable Long id) {
        ReservationResponse response = reservationService.getReservationDetails(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Reservation details retrieved successfully"));
    }
}
