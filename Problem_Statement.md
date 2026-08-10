# Problem Statement: Event Ticketing and Seat Booking Platform

## 1. Executive Summary
In the modern entertainment and event industry, customers often face frustrating booking experiences due to clunky UI workflows, real-time availability sync issues, and risk of double-booking seats. Simultaneously, event organizers struggle with fragmented tools for venue setup, seat tier pricing, ticket sales tracking, and revenue management. Administrators lack centralized control to audit activities, manage platform users, and approve event listings.

This project delivers a robust, secure, production-grade **Event Ticketing and Seat Booking Platform** built with **Java 17**, **Spring Boot 3.x**, **MySQL 8**, and **React.js (Vite + Bootstrap 5)**.

---

## 2. Key Problem Areas Addressed
1. **Seat Selection & Double-Booking Prevention**:
   - Traditional platforms struggle with concurrent transactions when multiple users attempt to reserve the same high-demand seat at the exact same time.
   - *Solution*: A transaction-isolated reservation engine utilizing pessimistic database locks, unique constraint enforcement, and temporary 5-minute hold timeouts with automatic background cleanup.

2. **Unified Role-Based Portals**:
   - Disconnected workflows for Customers, Event Organizers, and Admins.
   - *Solution*: Role-Based Access Control (RBAC) with Spring Security + JWT, providing tailored dashboards for customers (booking history, downloadable QR tickets), organizers (venue/seat setup, real-time sales analytics, CSV export), and admins (user governance, event approvals, audit logs).

3. **Simulated Sandbox Payments & Verification**:
   - Safe academic payment processing with idempotent transaction logging.
   - Tamper-proof digital tickets with unique verification tokens and QR code validation endpoints.

4. **Smart Event Discovery**:
   - Rule-based recommendation engine prioritizing user category preferences, city location, upcoming dates, and event popularity.

---

## 3. Scope & Objectives
- **Target OS & Environment**: Windows 11 / Cross-Platform, Java 17+, Maven 3.9+, Node 24+, MySQL 8.0.
- **Academic Alignment**: Semester 5 College Capstone Project adhering to Reviews I, II, and III milestones.
- **Architectural Standards**: Clean Layered Architecture (Controller -> Service -> Repository -> JPA -> MySQL), DTO pattern, standard envelope response format (`{ success, data, message }`), comprehensive unit test coverage (JUnit 5 + Mockito).
