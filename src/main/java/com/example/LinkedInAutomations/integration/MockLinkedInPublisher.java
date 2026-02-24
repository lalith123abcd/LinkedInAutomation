package com.example.LinkedInAutomations.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MockLinkedInPublisher implements LinkedInPublisher {

    @Override
    public void publish(String accessToken, String content) {

        log.info("Mock Publishing to LinkedIn...");
        log.info("Using Token: {}", accessToken);
        log.info("Content: {}", content);

        // simulate success
    }
}