# Eventify — Testing Strategy & Quality Assurance

## Unit & Integration Testing Strategy

### Frameworks & Tools
- **JUnit 5**: Core testing framework.
- **Mockito**: Service layer mock testing.
- **Spring Boot Test**: Integration testing with `@SpringBootTest` and `@DataJpaTest`.

### Test Coverage Targets
- Target >= 40% code coverage for core service methods.
- Essential test suites:
  1. `AuthServiceTest`: Registration validation, duplicate email prevention, password hashing, JWT generation.
  2. `EventServiceTest`: Event CRUD, publication rules, status transitions.
  3. `SeatReservationServiceTest`: Concurrent seat hold, pessimistic locking, hold expiration.
  4. `PaymentServiceTest`: Idempotent payment simulation, status handling.
  5. `TicketServiceTest`: Ticket issuance, QR token hash generation, verification logic.
  6. `RecommendationServiceTest`: Rule-based event filtering and popularity scoring.

## Continuous Integration Workflow
GitHub Actions (`.github/workflows/ci.yml`) automatically executes `mvn clean verify` on every push and pull request to ensure zero build breaks.
