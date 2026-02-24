package com.example.LinkedInAutomations.service;

import com.example.LinkedInAutomations.entity.LinkedInAccount;
import com.example.LinkedInAutomations.entity.User;
import com.example.LinkedInAutomations.repository.LinkedInAccountRepository;
import com.example.LinkedInAutomations.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LinkedInService {

    private final LinkedInAccountRepository linkedInAccountRepository;
    private final UserRepository userRepository;

    public LinkedInAccount connectAccount(String email) {

        // 1️⃣ Find system user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2️⃣ Check if already connected
        linkedInAccountRepository.findByUserId(user.getId())
                .ifPresent(account -> {
                    throw new RuntimeException("LinkedIn already connected");
                });

        // 3️⃣ Mock LinkedIn response (later replace with real OAuth)
        LinkedInAccount account = LinkedInAccount.builder()
                .id(UUID.randomUUID())
                .userId(user.getId())
                .linkedinUserId("mock-linkedin-id")
                .accessToken("mock-access-token")
                .refreshToken("mock-refresh-token")
                .expiresAt(Instant.now().plusSeconds(3600))
                .build();

        // 4️⃣ Save to DB
        return linkedInAccountRepository.save(account);
    }
}