package com.eventbooking.scheduler;

import com.eventbooking.entity.Reservation;
import com.eventbooking.enums.ReservationStatus;
import com.eventbooking.repository.ReservationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Scheduled Runner executing background cleanup for expired 10-minute temporary seat holds.
 */
@Component
public class ReservationCleanupScheduler {

    private final ReservationRepository reservationRepository;

    public ReservationCleanupScheduler(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Scheduled(fixedDelay = 60000) // Runs every 60 seconds
    @Transactional
    public void cleanupExpiredHolds() {
        LocalDateTime now = LocalDateTime.now();
        List<Reservation> expiredReservations = reservationRepository.findByStatusAndExpiresAtBefore(ReservationStatus.PENDING, now);

        if (!expiredReservations.isEmpty()) {
            for (Reservation res : expiredReservations) {
                res.setStatus(ReservationStatus.EXPIRED);
            }
            reservationRepository.saveAll(expiredReservations);
            System.out.println("[Eventify Scheduler] Released " + expiredReservations.size() + " expired seat reservations automatically.");
        }
    }
}
