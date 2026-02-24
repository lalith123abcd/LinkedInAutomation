package com.example.LinkedInAutomations.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "drafts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Draft {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID personaId;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private String styleType; // INSIGHT / STORY / CHECKLIST

    @Column(columnDefinition = "TEXT", nullable = false)
    private String generatedText;

    @Column(nullable = false)
    private String status; // DRAFT / APPROVED / REJECTED

    @CreationTimestamp
    private Instant createdAt;
}