package com.example.LinkedInAutomations.controller;

import com.example.LinkedInAutomations.entity.LinkedInAccount;
import com.example.LinkedInAutomations.service.LinkedInService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/linkedin")
public class LinkedInController {
    private final LinkedInService linkedInService;
    @PostMapping("/connect")
    public LinkedInAccount connect(Authentication authentication) {

        String email = authentication.getName();

        return linkedInService.connectAccount(email);
    }
}
