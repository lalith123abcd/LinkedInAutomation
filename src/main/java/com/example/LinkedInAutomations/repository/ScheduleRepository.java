package com.example.LinkedInAutomations.repository;

import com.example.LinkedInAutomations.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {

    List<Schedule> findByStatusAndScheduledTimeBefore(Schedule.ScheduleStatus status, Instant time);
}