package com.eventbooking.dto.request;

import jakarta.validation.constraints.NotBlank;

public class VerifyTicketRequest {

    @NotBlank(message = "Ticket token or reference is required")
    private String token;

    public VerifyTicketRequest() {}

    public VerifyTicketRequest(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
