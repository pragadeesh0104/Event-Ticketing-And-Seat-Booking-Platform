package com.eventbooking.repository;

import com.eventbooking.entity.Reservation;
import com.eventbooking.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByReservationReference(String reservationReference);
    List<Reservation> findByStatusAndExpiresAtBefore(ReservationStatus status, LocalDateTime now);
    List<Reservation> findByUserIdOrderByCreatedAtDesc(Long userId);
}
