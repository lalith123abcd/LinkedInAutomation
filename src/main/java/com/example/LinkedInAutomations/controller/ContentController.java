package com.example.LinkedInAutomations.controller;

import com.example.LinkedInAutomations.dto.ContentRequestDTO;
import com.example.LinkedInAutomations.entity.Content;
import com.example.LinkedInAutomations.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @PostMapping("/generate-post")
    public Content generatePost(
            Authentication authentication,
            @RequestBody ContentRequestDTO dto) {

        return contentService.generatePost(authentication.getName(), dto);
    }

    @GetMapping("/history")
    public List<Content> getHistory(Authentication authentication) {
        return contentService.getHistory(authentication.getName());
    }
}