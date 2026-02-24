package com.example.LinkedInAutomations.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "personas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Persona {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID userId;   // FK to users.id

    @Column(nullable = false)
    private String tone;   // Professional, Casual, Direct, etc.

    @Column(columnDefinition = "TEXT")
    private String writingStyle;

    @Column(columnDefinition = "TEXT")
    private String dos;

    @Column(columnDefinition = "TEXT")
    private String donts;

    @Column(columnDefinition = "TEXT")
    private String experienceSummary;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}