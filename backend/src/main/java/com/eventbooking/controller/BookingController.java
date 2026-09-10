package com.eventbooking.controller;

import com.eventbooking.dto.request.CheckoutRequest;
import com.eventbooking.dto.response.ApiResponse;
import com.eventbooking.dto.response.BookingDto;
import com.eventbooking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Booking and Checkout Endpoints (/api/v1/bookings).
 */
@RestController
@RequestMapping("/api/v1/bookings")
@Tag(name = "Booking & Checkout", description = "Checkout settlement and user booking history endpoints")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/checkout")
    @Operation(summary = "Complete checkout and simulated sandbox payment to issue confirmed tickets")
    public ResponseEntity<ApiResponse<BookingDto>> checkout(
            @Valid @RequestBody CheckoutRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        BookingDto booking = bookingService.checkout(request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(booking, "Payment processed successfully. Booking confirmed."));
    }

    @GetMapping("/my")
    @Operation(summary = "Get user's booking history")
    public ResponseEntity<ApiResponse<List<BookingDto>>> getMyBookings(@AuthenticationPrincipal UserDetails userDetails) {
        List<BookingDto> bookings = bookingService.getUserBookings(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(bookings, "User bookings retrieved successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking details by ID")
    public ResponseEntity<ApiResponse<BookingDto>> getBookingById(@PathVariable Long id) {
        BookingDto booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(ApiResponse.success(booking, "Booking details retrieved successfully"));
    }
}
