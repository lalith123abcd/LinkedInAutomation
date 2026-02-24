package com.example.LinkedInAutomations.controller;

import com.example.LinkedInAutomations.dto.DraftRequestDTO;
import com.example.LinkedInAutomations.entity.Draft;
import com.example.LinkedInAutomations.service.DraftService;
import com.example.LinkedInAutomations.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PublishController {

    private final ScheduleService scheduleService;

    @PostMapping("/publish-now/{draftId}")
    public ResponseEntity<?> publishNow(@PathVariable UUID draftId,
                                        Principal principal) {

        scheduleService.publishNow(draftId, principal.getName());
        return ResponseEntity.ok("Post scheduled for immediate publishing");
    }
}