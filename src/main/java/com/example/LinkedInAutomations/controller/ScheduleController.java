package com.example.LinkedInAutomations.controller;

import com.example.LinkedInAutomations.dto.ScheduleRequestDTO;
import com.example.LinkedInAutomations.entity.Schedule;
import com.example.LinkedInAutomations.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public Schedule createSchedule(
            Authentication authentication,
            @RequestBody ScheduleRequestDTO dto) {

        return scheduleService.createSchedule(authentication.getName(), dto);
    }
}