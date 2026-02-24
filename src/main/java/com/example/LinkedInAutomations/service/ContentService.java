package com.example.LinkedInAutomations.service;

import com.example.LinkedInAutomations.dto.ContentRequestDTO;
import com.example.LinkedInAutomations.entity.Content;
import com.example.LinkedInAutomations.entity.Persona;
import com.example.LinkedInAutomations.entity.User;
import com.example.LinkedInAutomations.repository.ContentRepository;
import com.example.LinkedInAutomations.repository.PersonaRepository;
import com.example.LinkedInAutomations.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final PersonaRepository personaRepository;

    public Content generatePost(String email, ContentRequestDTO dto) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Persona persona = personaRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Persona not found"));

        // Simple Template-Based Generation (LLM later)
        String generated = buildPost(persona, dto.getTopic());

        Content content = Content.builder()
                .userId(user.getId())
                .personaId(persona.getId())
                .contentType("POST")
                .topic(dto.getTopic())
                .generatedText(generated)
                .build();

        return contentRepository.save(content);
    }

    private String buildPost(Persona persona, String topic) {

        return String.format(
                "Tone: %s\n\nStyle: %s\n\nTopic: %s\n\n%s\n\n#LinkedIn #Growth",
                persona.getTone(),
                persona.getWritingStyle(),
                topic,
                persona.getExperienceSummary()
        );
    }

    public List<Content> getHistory(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return contentRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
    }
}