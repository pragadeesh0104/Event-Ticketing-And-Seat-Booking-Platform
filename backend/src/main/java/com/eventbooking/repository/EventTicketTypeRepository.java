package com.eventbooking.repository;

import com.eventbooking.entity.EventTicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventTicketTypeRepository extends JpaRepository<EventTicketType, Long> {
    List<EventTicketType> findByEventId(Long eventId);
}
