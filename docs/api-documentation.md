# Eventify — REST API Documentation

Base Path: `/api/v1`

## Response Envelope Standard
All API responses return a uniform JSON format:

### Success Example (200 OK / 201 Created)
```json
{
  "success": true,
  "data": { ... },
  "message": "Operation completed successfully"
}
```

### Error Example (400 Bad Request / 409 Conflict)
```json
{
  "success": false,
  "data": null,
  "message": "Selected seat is no longer available",
  "errors": ["Seat A-12 is currently held by another customer"]
}
```

---

## Core Endpoint Catalog

### 1. Authentication (`/api/v1/auth`)
- `POST /register` - Register new customer or organizer.
- `POST /login` - Authenticate user and receive JWT token.
- `GET /me` - Get current authenticated user profile.

### 2. Event Discovery (`/api/v1/events`)
- `GET /` - Public list of published events with search, category, date, city filtering, and pagination.
- `GET /{id}` - Get full event details, venue specs, and ticket prices.
- `POST /` - Create event (Organizer/Admin).
- `PUT /{id}` - Update event details.
- `PATCH /{id}/status` - Update event publication status (`DRAFT`, `PUBLISHED`, `CANCELLED`).

### 3. Venues & Seats (`/api/v1/venues` & `/api/v1/events/{id}/seats`)
- `GET /venues` - List all active venues.
- `POST /venues` - Create new venue (Admin).
- `GET /events/{eventId}/seats` - Fetch seat layout and availability for an event.

### 4. Reservations & Booking (`/api/v1/reservations` & `/api/v1/bookings`)
- `POST /reservations` - Create 10-minute temporary seat hold.
- `POST /bookings/checkout` - Convert reservation into confirmed booking via payment sandbox.
- `GET /bookings/my` - Fetch user's booking history.
- `POST /bookings/{id}/cancel` - Cancel booking and trigger refund process.

### 5. Payments (`/api/v1/payments`)
- `POST /payments/demo` - Process simulated sandbox payment (Success / Failure).

### 6. Tickets & Verification (`/api/v1/tickets`)
- `GET /tickets/my` - List customer's digital tickets.
- `POST /tickets/verify` - Verify ticket QR token (Organizer/Admin Scanner).

### 7. Recommendations (`/api/v1/recommendations`)
- `GET /` - Smart event discovery recommendations.
