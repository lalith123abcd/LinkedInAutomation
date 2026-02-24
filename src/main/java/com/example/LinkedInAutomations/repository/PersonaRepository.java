package com.example.LinkedInAutomations.repository;

import com.example.LinkedInAutomations.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonaRepository extends JpaRepository<Persona, UUID> {

    Optional<Persona> findByUserId(UUID userId);
}