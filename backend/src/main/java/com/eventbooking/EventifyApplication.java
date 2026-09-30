package com.eventbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Eventify - Event Ticketing and Seat Booking Platform Main Application Entry Point.
 *
 * @author Senior Full Stack Engineering Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableScheduling
public class EventifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventifyApplication.class, args);
    }
}
