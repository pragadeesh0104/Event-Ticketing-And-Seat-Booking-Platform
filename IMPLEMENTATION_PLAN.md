# Implementation Plan: Event Ticketing and Seat Booking Platform

## 1. Overview & Architecture Strategy
The Event Ticketing and Seat Booking Platform is designed as a Semester 5 capstone Java full-stack web application.
- **Backend Architecture**: Layered Architecture (`com.eventbooking.*`) with Spring Boot 3.x, Spring Security, JWT authentication, Spring Data JPA, Hibernate, and MySQL 8.
- **Frontend Architecture**: React.js with Vite, React Router v6, Bootstrap 5, Axios, Recharts, and React Toastify.
- **Concurrency & Booking Safety**: Transaction-isolated seat reservation engine with pessimistic database locking (`PESSIMISTIC_WRITE`) on active `booking_seats` combined with a 5-minute hold timeout scheduled cleanup service to guarantee zero double-bookings.

---

## 2. Phase-by-Phase Roadmap

### Phase 1: Days 1–10 — Planning, Architecture & MVP Foundations (Current Stage)
- [x] Environment inspection and tool verification (Java 25/17, Maven 3.9, Node 24, MySQL 8).
- [x] Creation of core project metadata (`Problem_Statement.md`, `.env.example`, `.gitignore`, `LICENSE`, `CHANGELOG.md`).
- [x] Diagram source generation (`docs/diagrams/` - Architecture, ER Diagram, UML Class Diagram).
- [x] Database migration setup (`V1__initial_schema.sql`).
- [ ] Initialize Spring Boot backend Maven `pom.xml` and application configuration (`application.yml`).
- [ ] Implement Module 1: Auth & User Management (JWT, BCrypt, Spring Security, `ROLE_CUSTOMER`, `ROLE_ORGANIZER`, `ROLE_ADMIN`).
- [ ] Initialize React frontend (`Vite + Bootstrap 5`) and establish initial auth routing.

### Phase 2: Days 12–28 — Core Business Modules & Seating Engine
- [ ] Module 2: Event Discovery & Listing (Search by keyword, city, category, date filter, status lifecycle: `DRAFT`, `PENDING_APPROVAL`, `PUBLISHED`, `CANCELLED`).
- [ ] Module 3: Venue & Seat Management (Grid rendering, row/number mapping, price tiers: `STANDARD`, `PREMIUM`, `VIP`).
- [ ] Module 4: Concurrency-Safe Seat Reservation Engine (5-minute temporary hold, pessimistic lock, price recalculation on server).
- [ ] Module 5: Booking & Checkout (Booking reference generation, summary calculation).

### Phase 3: Days 29–40 — Payments, Tickets, Dashboards & Analytics
- [ ] Module 6: Idempotent Simulated Payment Sandbox.
- [ ] Module 7: Ticket Generation & QR Verification endpoint.
- [ ] Module 8: Notifications & SMTP Email Integration.
- [ ] Module 9: Role-Based Dashboards & Analytics (Recharts, real DB aggregate queries).
- [ ] Module 10: Reports & CSV Export + Audit Logs.

### Phase 4 & 5: Days 42–56 — Smart Recommendation Enhancement (Day 60 Feature)
- [ ] Rule-Based Recommendation Engine in Java (`RecommendationService`) suggesting events based on user preferences, city, upcoming dates, and popularity.
- [ ] Frontend Recommendation UI integration.

### Phase 6: Days 57–60 — Verification, CI/CD, Deployment & Final Viva Prep
- [ ] Comprehensive JUnit 5 + Mockito tests for service layers (>= 40% coverage).
- [ ] GitHub Actions CI/CD workflows (`.github/workflows/backend.yml`, `.github/workflows/frontend.yml`).
- [ ] Final deployment guide (Vercel/Netlify for frontend, Render/Railway for backend, MySQL Cloud).
