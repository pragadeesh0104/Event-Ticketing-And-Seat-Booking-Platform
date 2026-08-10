# Eventify — Event Ticketing and Seat Booking Platform

> A production-grade, full-stack web application for discovering events, selecting venue seats in real time, executing transaction-isolated reservations, and issuing verifiable digital QR tickets.

---

## 1. Project Title
**Eventify — Event Ticketing and Seat Booking Platform**

## 2. Problem Statement
Many existing event platforms struggle with real-time availability sync, leading to duplicate seat bookings during peak ticket sales. Event organizers often lack a unified dashboard for venue seat layout configuration, live reservation tracking, and revenue analytics. Eventify resolves these challenges by combining transaction-isolated seat reservations (using MySQL 8 pessimistic locking and Flyway migrations) with role-tailored dashboards and verifiable QR tickets.

## 3. Objectives
- **Zero Double-Booking Guarantee**: Use pessimistic database locks (`PESSIMISTIC_WRITE`) and unique DB constraints to prevent overlapping seat reservations.
- **Interactive Seating Chart**: Provide a dynamic seat selector UI with 10-minute hold expiration timers.
- **Role-Based Access Control**: Secure endpoints for Customers, Organizers, and Admins via Spring Security + JWT.
- **Simulated Payment Sandbox**: Enable academic payment workflows with idempotent transaction logging.
- **Smart Recommendations**: Implement a rule-based discovery engine prioritizing user preferences, city location, and event popularity.

## 4. Features
- 🎟️ **Customer Portal**: Event search, category & date filtering, interactive seat layout chart, temporary 10-min hold, demo payment checkout, digital QR tickets, booking cancellation.
- 📊 **Organizer Portal**: Event creation & management, poster URL configuration, ticket price tiers, venue seat maps, sales analytics charts (Recharts), CSV report export.
- 🛡️ **Administrator Portal**: User account status toggle (Active/Deactive), event approval moderation, platform statistics, ticket verification scanner, system audit logs.

## 5. Technology Stack
- **Backend**: Java 17, Spring Boot 3.3.x, Spring Security, JWT (JJWT), Spring Data JPA, Hibernate, Bean Validation, Maven, Spring Boot Actuator, springdoc-openapi (Swagger UI), ZXing (QR Code).
- **Frontend**: React.js with Vite, JavaScript (ES6+), Bootstrap 5, Axios, React Router v6, Recharts, Lucide React icons.
- **Database**: MySQL 8.0, Flyway migrations.
- **Testing & CI/CD**: JUnit 5, Mockito, Spring Boot Test, GitHub Actions.

## 6. System Architecture
See [docs/architecture.md](file:///d:/prgadesh/docs/architecture.md) and [docs/diagrams/architecture.mmd](file:///d:/prgadesh/docs/diagrams/architecture.mmd).
```
React.js Frontend ──► Axios (JWT Header) ──► Spring Security Filter ──► Controllers ──► Services ──► JPA/Hibernate ──► MySQL 8
```

## 7. Database Schema
See [docs/database-design.md](file:///d:/prgadesh/docs/database-design.md) and [docs/diagrams/er_diagram.mmd](file:///d:/prgadesh/docs/diagrams/er_diagram.mmd).
Contains 13 normalized tables: `users`, `venues`, `events`, `seats`, `event_ticket_types`, `reservations`, `reservation_seats`, `bookings`, `booking_seats`, `payments`, `tickets`, `notifications`, and `audit_logs`.

## 8. Installation and Setup

### Prerequisites
- Java 17+ SDK
- Maven 3.9+
- Node.js 18+ & npm
- MySQL 8.0 Server running on port 3306

### Local Setup Instructions (Windows Terminal / PowerShell)

1. **Database Setup**:
   Ensure MySQL service is running. Create the database:
   ```powershell
   & "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p -e "CREATE DATABASE IF NOT EXISTS event_booking_db;"
   ```

2. **Backend Setup**:
   Navigate to the `backend` directory and compile:
   ```powershell
   cd d:\prgadesh\backend
   mvn clean spring-boot:run
   ```
   The API server will run at `http://localhost:8080`. Swagger documentation is available at `http://localhost:8080/swagger-ui.html`.

3. **Frontend Setup**:
   Navigate to the `frontend` directory, install dependencies, and start dev server:
   ```powershell
   cd d:\prgadesh\frontend
   npm install
   npm run dev
   ```
   Access the web app at `http://localhost:5173`.

## 9. Environment Variables
Refer to [.env.example](file:///d:/prgadesh/.env.example) for placeholder values.
Key variables: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`, `JWT_EXPIRATION_MS`, `SERVER_PORT`.

## 10. API Documentation
Detailed REST endpoint descriptions are documented in [docs/api-documentation.md](file:///d:/prgadesh/docs/api-documentation.md) and interactively available via Swagger UI (`http://localhost:8080/swagger-ui.html`).

## 11. Running Tests
Execute unit and integration tests:
```powershell
cd d:\prgadesh\backend
mvn clean verify
```

## 12. Deployment
Detailed hosting procedures for Vercel/Netlify (Frontend) and Render/Railway (Backend) are provided in [docs/deployment-guide.md](file:///d:/prgadesh/docs/deployment-guide.md).

## 13. Screenshots
*(Placeholder for UI screenshots captured during final demonstration review).*

## 14. Known Limitations
- Payment gateway uses a simulated academic sandbox rather than live Stripe/Razorpay processing.
- SMTP email sending requires user-configured host credentials; in-app notifications operate independently if credentials are absent.

## 15. Future Enhancements
- Live WebSocket push updates for seat map selection state.
- Multiple payment provider gateway adapters.

## 16. License
Released under the [MIT License](file:///d:/prgadesh/LICENSE).
