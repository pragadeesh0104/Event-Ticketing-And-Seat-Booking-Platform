package com.eventbooking.repository;

import com.eventbooking.entity.BookingSeat;
import com.eventbooking.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {
    List<BookingSeat> findByBookingId(Long bookingId);

    @Query("SELECT bs FROM BookingSeat bs JOIN bs.booking b WHERE bs.event.id = :eventId AND b.status = :status")
    List<BookingSeat> findConfirmedSeatsForEvent(
            @Param("eventId") Long eventId,
            @Param("status") BookingStatus status
    );

    boolean existsByEventIdAndSeatId(Long eventId, Long seatId);
}
