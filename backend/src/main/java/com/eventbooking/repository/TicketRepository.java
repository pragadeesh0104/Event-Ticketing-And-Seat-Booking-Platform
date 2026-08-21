package com.eventbooking.repository;

import com.eventbooking.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByTicketNumber(String ticketNumber);
    Optional<Ticket> findByVerificationTokenHash(String verificationTokenHash);
    List<Ticket> findByBookingUserId(Long userId);
    List<Ticket> findByBookingId(Long bookingId);
}
