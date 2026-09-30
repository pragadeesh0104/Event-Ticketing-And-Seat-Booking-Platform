# Eventify — Functional & Non-Functional Requirements

## 1. Functional Requirements

### User & Security Management
- **FR-1.1 Registration & Authentication**: Support registration for Customers and Organizers with BCrypt password hashing.
- **FR-1.2 Authorization**: Role-Based Access Control (RBAC) via JWT for `ROLE_CUSTOMER`, `ROLE_ORGANIZER`, `ROLE_ADMIN`.
- **FR-1.3 Profile & Password Management**: Users can update personal details and change passwords securely.

### Event Discovery & Venues
- **FR-2.1 Event Browsing**: Public search by keyword, city, category, date, and price range.
- **FR-2.2 Venue Configuration**: Venues have capacity, address, city, and seating section configurations.
- **FR-2.3 Event Lifecycle**: Status management (`DRAFT`, `PENDING_APPROVAL`, `PUBLISHED`, `CANCELLED`, `COMPLETED`).

### Seating & Concurrency
- **FR-3.1 Interactive Map**: Visual seat selection layout rendered dynamically by row and seat number.
- **FR-3.2 Temporary Hold**: 10-minute temporary seat hold with real-time countdown timer.
- **FR-3.3 Concurrency Control**: Transactional seat isolation guaranteeing zero double-bookings.

### Payments, Tickets & Notifications
- **FR-4.1 Payment Sandbox**: Idempotent demo payment gateway with simulated success/failure outcomes.
- **FR-4.2 QR Ticket Issuance**: Generation of unique digital tickets with verifiable QR tokens.
- **FR-4.3 Ticket Verification**: Scanner / Backend verification endpoint (`/api/v1/tickets/verify`).
- **FR-4.4 Notifications**: In-app notifications for holds, payments, cancellations, and ticket issuance.

### Dashboards & Analytics
- **FR-5.1 Organizer Analytics**: Total sales, revenue totals, remaining seat counts, CSV export.
- **FR-5.2 Admin Governance**: User status toggling, event moderation queue, platform stats, audit logs.
- **FR-5.3 Smart Recommendations**: Rule-based discovery based on category preferences, city, and event popularity.

## 2. Non-Functional Requirements
- **NFR-1 Security**: Passwords hashed with BCrypt (strength 10+), secrets stored in environment variables, sanitized SQL via JPA.
- **NFR-2 Performance**: API response latency under 300ms for seat availability queries.
- **NFR-3 Maintainability**: Clean layered Java architecture, Maven build, Flyway schema versioning.
- **NFR-4 Responsiveness**: Mobile, tablet, and desktop accessible UI built with Bootstrap 5.
