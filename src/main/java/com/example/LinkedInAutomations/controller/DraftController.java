package com.example.LinkedInAutomations.controller;

import com.example.LinkedInAutomations.dto.DraftRequestDTO;
import com.example.LinkedInAutomations.entity.Draft;
import com.example.LinkedInAutomations.service.DraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/draft")
@RequiredArgsConstructor
public class DraftController {

    private final DraftService draftService;

    @PostMapping("/generate")
    public List<Draft> generateDrafts(
            Authentication authentication,
            @RequestBody DraftRequestDTO dto) {

        return draftService.generateDrafts(authentication.getName(), dto);
    }

    @PostMapping("/{draftId}/approve")
    public Draft approveDraft(
            Authentication authentication,
            @PathVariable UUID draftId) {

        return draftService.approveDraft(authentication.getName(), draftId);
    }

    @GetMapping("/my")
    public List<Draft> getMyDrafts(Authentication authentication) {
        return draftService.getUserDrafts(authentication.getName());
    }
}