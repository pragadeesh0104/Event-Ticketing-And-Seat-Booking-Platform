# Eventify — System Architecture Specification

## Architecture Overview
Eventify utilizes a classic multi-tier layered full-stack architecture:

```
[ React.js Frontend (Vite + Bootstrap 5) ]
                    │  (REST API calls via Axios + JWT Authorization Header)
                    ▼
[ Spring Security Gateway / JwtAuthenticationFilter ]
                    │
                    ▼
[ Spring Boot REST Controller Layer (/api/v1/*) ]
                    │  (DTO Request Validation & Response Envelopes)
                    ▼
[ Service Layer & Seat Reservation Engine ] ◄──► [ Scheduled Cleanup Runner ]
                    │  (Business Logic, Transaction Management & Pessimistic Locks)
                    ▼
[ Spring Data JPA & Hibernate ORM Layer ]
                    │  (Parameterized Queries & Entity Mappings)
                    ▼
[ MySQL 8 Database (Managed Schema via Flyway) ]
```

## Security Architecture
1. **Authentication**: State-less JWT issued on `/api/v1/auth/login`.
2. **Authorization**: Spring Security `@PreAuthorize` annotations on service/controller layers.
3. **Password Protection**: BCrypt hashing with standard salt rounds.
4. **Data Integrity**: Enforce backend ownership checks for Organizer and Admin resources.

## Seat Reservation & Concurrency Engine Architecture
- **Isolation Level**: Read Committed with `PESSIMISTIC_WRITE` on `reservation_seats` / `booking_seats`.
- **Hold Expiration**: Temporary 10-minute hold with `expires_at` timestamp.
- **Background Cleanup**: `@Scheduled(fixedDelay = 60000)` runner automatically releases expired holds.
