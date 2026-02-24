package com.example.LinkedInAutomations.service;

import com.example.LinkedInAutomations.dto.PersonaRequestDTO;
import com.example.LinkedInAutomations.entity.Persona;
import com.example.LinkedInAutomations.entity.User;
import com.example.LinkedInAutomations.repository.PersonaRepository;
import com.example.LinkedInAutomations.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonaService {

    private final PersonaRepository personaRepository;
    private final UserRepository userRepository;

    public Persona saveOrUpdatePersona(String email, PersonaRequestDTO dto) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return personaRepository.findByUserId(user.getId())
                .map(existing -> {
                    existing.setTone(dto.getTone());
                    existing.setWritingStyle(dto.getWritingStyle());
                    existing.setDos(dto.getDos());
                    existing.setDonts(dto.getDonts());
                    existing.setExperienceSummary(dto.getExperienceSummary());
                    return personaRepository.save(existing);
                })
                .orElseGet(() -> {
                    Persona persona = Persona.builder()

                            .userId(user.getId())
                            .tone(dto.getTone())
                            .writingStyle(dto.getWritingStyle())
                            .dos(dto.getDos())
                            .donts(dto.getDonts())
                            .experienceSummary(dto.getExperienceSummary())
                            .build();

                    return personaRepository.save(persona);
                });
    }

    public Persona getPersona(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return personaRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Persona not found"));
    }
}