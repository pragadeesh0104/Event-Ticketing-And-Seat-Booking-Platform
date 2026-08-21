package com.eventbooking.repository;

import com.eventbooking.entity.Event;
import com.eventbooking.enums.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    Page<Event> findByStatus(EventStatus status, Pageable pageable);
    List<Event> findByOrganizerId(Long organizerId);
    List<Event> findByStatusAndStartTimeAfterOrderByStartTimeAsc(EventStatus status, LocalDateTime now);
}
