# PROJECT_PLAN.md — Eventify Platform

## Project Overview
**Eventify — Event Ticketing and Seat Booking Platform** is a Semester 5 capstone full-stack web application designed using Java 17, Spring Boot 3.x, MySQL 8, and React.js with Vite + Bootstrap 5.

---

## Master Milestone Checklist

### Milestone 1: Environment, Architecture & Database Foundation
- [x] Environment & Dev Tools Check (Java 25/17, Maven 3.9, Node 24, MySQL 8.0, Git).
- [x] `Problem_Statement.md`, `.env.example`, `.gitignore`, `LICENSE`, `CHANGELOG.md`.
- [x] Architecture, ER, and UML Class Diagrams in `docs/diagrams/`.
- [x] Flyway V1 13-table MySQL migration schema (`V1__initial_schema.sql`).
- [x] `PROJECT_PLAN.md` and `IMPLEMENTATION_STATUS.md`.

### Milestone 2: Backend Core (Spring Boot 3.x)
- [ ] Maven `pom.xml` configuration with dependencies (Web, Security, Data JPA, MySQL, Flyway, JJWT, Validation, Actuator, Springdoc OpenAPI, ZXing QR).
- [ ] `application.yml` with dev/prod profiles, JPA, Flyway, JWT, SMTP settings.
- [ ] Security & Auth module (`com.eventbooking.security.*`, `AuthController`, `AuthService`, BCrypt, JWT Filter).
- [ ] Entity, DTO, Repository, Enum, and Exception package structure.
- [ ] Database Seed Runner (`DataInitializer.java`) with initial Admin, Organizer, Customer users, sample Venues, Seats, and Events.
- [ ] Event Discovery & Venue API Endpoints.

### Milestone 3: Seat Reservation & Concurrency Engine
- [ ] Concurrency-safe seat reservation (`PESSIMISTIC_WRITE` lock / unique reservation constraints).
- [ ] 10-minute hold expiration logic & Scheduled cleanup runner (`@Scheduled`).
- [ ] Booking & Checkout API endpoints.
- [ ] Demo Payment Sandbox API (`/api/v1/payments/demo`).

### Milestone 4: Tickets, QR Codes & Notifications
- [ ] Digital ticket issuance & ZXing QR code generation (`/api/v1/tickets/*`).
- [ ] Backend QR verification endpoint (`/api/v1/tickets/verify`).
- [ ] In-app notification service & optional SMTP email integration.
- [ ] Audit Logging service.

### Milestone 5: Frontend Core (React + Vite + Bootstrap 5)
- [ ] React project structure (`api`, `components`, `context`, `layouts`, `pages`, `routes`, `utils`).
- [ ] Auth Context, Axios interceptors for JWT, and Route Guards (`Customer`, `Organizer`, `Admin`).
- [ ] Modern Homepage, Event Discovery, Search & Filters.
- [ ] Interactive Seating Layout Component with real seat map & hold countdown.

### Milestone 6: Dashboards, Analytics & Day 60 Smart Recommendation
- [ ] Customer Dashboard (Bookings, Tickets, Notifications, QR View).
- [ ] Organizer Dashboard (Event CRUD, Sales charts with Recharts, CSV Export).
- [ ] Admin Dashboard (System health, User status toggle, Event moderation, Audit logs).
- [ ] Smart Event Discovery & Booking Insights recommendation engine.

### Milestone 7: Testing, CI/CD & Deployment
- [ ] Service unit tests (JUnit 5 + Mockito, >= 40% coverage).
- [ ] GitHub Actions CI workflows (`.github/workflows/ci.yml`).
- [ ] Production build verification (`mvn clean verify` & `npm run build`).
