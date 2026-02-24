package com.example.LinkedInAutomations.repository;

import com.example.LinkedInAutomations.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContentRepository extends JpaRepository<Content, UUID> {

    List<Content> findByUserIdOrderByCreatedAtDesc(UUID userId);
}