package com.example.LinkedInAutomations.service;

import com.example.LinkedInAutomations.dto.DraftRequestDTO;
import com.example.LinkedInAutomations.entity.Draft;
import com.example.LinkedInAutomations.entity.Persona;
import com.example.LinkedInAutomations.entity.User;
import com.example.LinkedInAutomations.repository.DraftRepository;
import com.example.LinkedInAutomations.repository.PersonaRepository;
import com.example.LinkedInAutomations.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DraftService {

    private final DraftRepository draftRepository;
    private final UserRepository userRepository;
    private final PersonaRepository personaRepository;

    public List<Draft> generateDrafts(String email, DraftRequestDTO dto) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Persona persona = personaRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Persona not found"));

        List<Draft> drafts = new ArrayList<>();

        drafts.add(createDraft(user, persona, dto.getTopic(), "INSIGHT"));
        drafts.add(createDraft(user, persona, dto.getTopic(), "STORY"));
        drafts.add(createDraft(user, persona, dto.getTopic(), "CHECKLIST"));

        return drafts;
    }

    private Draft createDraft(User user, Persona persona, String topic, String style) {

        String generatedText = buildText(persona, topic, style);

        Draft draft = Draft.builder()
                .userId(user.getId())
                .personaId(persona.getId())
                .topic(topic)
                .styleType(style)
                .generatedText(generatedText)
                .status("DRAFT")
                .build();

        return draftRepository.save(draft);
    }

    private String buildText(Persona persona, String topic, String style) {

        switch (style) {

            case "INSIGHT":
                return "💡 Insight on " + topic + "\n\n"
                        + persona.getExperienceSummary()
                        + "\n\nTone: " + persona.getTone()
                        + "\n#LinkedIn #Growth";

            case "STORY":
                return "📖 A quick story about " + topic + "\n\n"
                        + persona.getExperienceSummary()
                        + "\n\nLessons learned."
                        + "\n#Career";

            case "CHECKLIST":
                return "✅ Checklist for " + topic + "\n\n"
                        + "1. Stay consistent\n"
                        + "2. Improve daily\n"
                        + "3. Follow your principles\n\n"
                        + "Tone: " + persona.getTone();

            default:
                return topic;
        }
    }

    public Draft approveDraft(String email, UUID draftId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Draft draft = draftRepository.findById(draftId)
                .orElseThrow(() -> new RuntimeException("Draft not found"));

        if (!draft.getUserId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        draft.setStatus("APPROVED");

        return draftRepository.save(draft);
    }

    public List<Draft> getUserDrafts(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return draftRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
    }
}