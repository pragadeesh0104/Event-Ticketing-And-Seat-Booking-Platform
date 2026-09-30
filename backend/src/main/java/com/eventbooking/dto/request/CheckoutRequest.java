package com.eventbooking.dto.request;

import com.eventbooking.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;

public class CheckoutRequest {

    @NotNull(message = "Reservation ID is required")
    private Long reservationId;

    private PaymentMethod paymentMethod; // Defaults to DEMO_CARD

    private String idempotencyKey;

    private boolean simulateFailure; // For testing failed payment scenario

    public CheckoutRequest() {}

    public CheckoutRequest(Long reservationId, PaymentMethod paymentMethod, String idempotencyKey, boolean simulateFailure) {
        this.reservationId = reservationId;
        this.paymentMethod = paymentMethod;
        this.idempotencyKey = idempotencyKey;
        this.simulateFailure = simulateFailure;
    }

    public Long getReservationId() { return reservationId; }
    public void setReservationId(Long reservationId) { this.reservationId = reservationId; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }
    public boolean isSimulateFailure() { return simulateFailure; }
    public void setSimulateFailure(boolean simulateFailure) { this.simulateFailure = simulateFailure; }
}
