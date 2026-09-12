package com.eventbooking.controller;

import com.eventbooking.dto.request.VerifyTicketRequest;
import com.eventbooking.dto.response.ApiResponse;
import com.eventbooking.dto.response.TicketDto;
import com.eventbooking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Ticket Issuance and QR Code Verification Endpoints (/api/v1/tickets).
 */
@RestController
@RequestMapping("/api/v1/tickets")
@Tag(name = "Digital Tickets & QR Verification", description = "Ticket issuance and scanner verification endpoints")
public class TicketController {

    private final BookingService bookingService;

    public TicketController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/verify")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    @Operation(summary = "Verify ticket QR code token or ticket reference (Organizer/Admin)")
    public ResponseEntity<ApiResponse<TicketDto>> verifyTicket(@Valid @RequestBody VerifyTicketRequest request) {
        TicketDto ticket = bookingService.verifyTicket(request);
        return ResponseEntity.ok(ApiResponse.success(ticket, "Ticket verified successfully!"));
    }
}
