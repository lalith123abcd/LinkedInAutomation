package com.example.LinkedInAutomations.service;

import com.example.LinkedInAutomations.dto.ScheduleRequestDTO;
import com.example.LinkedInAutomations.entity.Draft;
import com.example.LinkedInAutomations.entity.Schedule;
import com.example.LinkedInAutomations.entity.User;
import com.example.LinkedInAutomations.repository.DraftRepository;
import com.example.LinkedInAutomations.repository.ScheduleRepository;
import com.example.LinkedInAutomations.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final DraftRepository draftRepository;
    private final UserRepository userRepository;

    public Schedule createSchedule(String email, ScheduleRequestDTO dto) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Draft draft = draftRepository.findById(dto.getDraftId())
                .orElseThrow(() -> new RuntimeException("Draft not found"));

        // Security check
        if (!draft.getUserId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        // Must be approved before scheduling
        if (!"APPROVED".equals(draft.getStatus())) {
            throw new RuntimeException("Draft must be approved before scheduling");
        }

        // Prevent scheduling in past
        if (dto.getScheduledTime().isBefore(Instant.now())) {
            throw new RuntimeException("Scheduled time must be in the future");
        }

        Schedule schedule = Schedule.builder()
                .draftId(draft.getId())
                .userId(user.getId())
                .scheduledTime(dto.getScheduledTime())
                .status(Schedule.ScheduleStatus.PENDING)
                .retryCount(0)
                .build();

        return scheduleRepository.save(schedule);
    }

    public Schedule publishNow(UUID draftId,String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Draft draft = draftRepository.findById(draftId)
                .orElseThrow(() -> new RuntimeException("Draft not found"));

        // Security check
        if (!draft.getUserId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        // Must be approved
        if (!"APPROVED".equals(draft.getStatus())) {
            throw new RuntimeException("Draft must be approved before publishing");
        }

        Schedule schedule = Schedule.builder()
                .draftId(draft.getId())
                .userId(user.getId())
                .scheduledTime(Instant.now()) // immediate
                .status(Schedule.ScheduleStatus.PENDING)
                .retryCount(0)
                .build();

        return scheduleRepository.save(schedule);
    }
}