package com.example.LinkedInAutomations.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;
@Entity
@Table(name = "schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID draftId;

    private UUID userId;

    private Instant scheduledTime;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    private int retryCount;

    @CreationTimestamp
    private Instant createdAt;

    public enum ScheduleStatus {
        PENDING,
        PROCESSING,
        POSTED,
        FAILED
    }
}