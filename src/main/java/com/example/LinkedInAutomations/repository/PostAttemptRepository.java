package com.example.LinkedInAutomations.repository;

import com.example.LinkedInAutomations.entity.PostAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostAttemptRepository extends JpaRepository<PostAttempt, UUID> {
}