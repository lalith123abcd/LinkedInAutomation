package com.example.LinkedInAutomations.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "linkedin_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkedInAccount {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID userId;   // FK to users.id

    @Column(nullable = false, unique = true)
    private String linkedinUserId;  // ID from LinkedIn API

    @Column(nullable = false, length = 2000)
    private String accessToken;  // 🔐 Should be encrypted later

    @Column(length = 2000)
    private String refreshToken; // 🔐 Optional but recommended

    @Column(nullable = false)
    private Instant expiresAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}