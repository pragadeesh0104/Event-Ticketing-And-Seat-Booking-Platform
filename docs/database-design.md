# Eventify — Database Design & Schema Specification

## Database Engine: MySQL 8.0

## Entity Relationship Overview
The database comprises 13 normalized relational tables managed via Flyway migration scripts in `backend/src/main/resources/db/migration/`:

1. `users`: Customer, Organizer, and Admin credentials, status, and role metadata.
2. `venues`: Venue names, addresses, cities, capacities, and active states.
3. `events`: Scheduled events hosted at venues by organizers with dates, prices, categories, and status (`DRAFT`, `PENDING_APPROVAL`, `PUBLISHED`, `CANCELLED`).
4. `seats`: Individual physical seat coordinates (venue_id, section_name, row_label, seat_number, seat_type).
5. `event_ticket_types`: Price and total quantity tier overrides per section/type per event.
6. `reservations`: Temporary seat holds with 10-minute expiration timestamps.
7. `reservation_seats`: Mapping between active reservations, events, and individual seat IDs.
8. `bookings`: Confirmed ticket bookings with unique booking reference codes.
9. `booking_seats`: Confirmed seat mappings with unique constraint (`event_id`, `seat_id`) to enforce zero double-bookings.
10. `payments`: Idempotent payment transactions linked to bookings with transaction reference codes.
11. `tickets`: Issued digital tickets with unique verification token hashes.
12. `notifications`: In-app notification messages sent to users.
13. `audit_logs`: Operational logs of administrative and system activities.

## Foreign Keys & Constraints
- `uk_venue_section_row_seat`: `UNIQUE(venue_id, section_name, row_label, seat_number)`
- `uk_event_seat_confirmed`: `UNIQUE(event_id, seat_id)` on `booking_seats` ensuring database-level prevention of duplicate seat booking.
