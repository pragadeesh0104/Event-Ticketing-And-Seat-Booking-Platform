package com.eventbooking.repository;

import com.eventbooking.entity.ReservationSeat;
import com.eventbooking.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, Long> {
    List<ReservationSeat> findByReservationId(Long reservationId);

    @Query("SELECT rs FROM ReservationSeat rs JOIN rs.reservation r WHERE rs.event.id = :eventId AND r.status = :status AND r.expiresAt > :now")
    List<ReservationSeat> findActiveHeldSeatsForEvent(
            @Param("eventId") Long eventId,
            @Param("status") ReservationStatus status,
            @Param("now") LocalDateTime now
    );
}
