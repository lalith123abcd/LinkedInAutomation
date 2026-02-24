package com.example.LinkedInAutomations.repository;

import com.example.LinkedInAutomations.entity.Draft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DraftRepository extends JpaRepository<Draft, UUID> {

    List<Draft> findByUserIdOrderByCreatedAtDesc(UUID userId);
}