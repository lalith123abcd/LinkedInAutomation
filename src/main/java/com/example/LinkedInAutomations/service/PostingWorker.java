package com.example.LinkedInAutomations.service;

import com.example.LinkedInAutomations.entity.*;
import com.example.LinkedInAutomations.integration.LinkedInPublisher;
import com.example.LinkedInAutomations.repository.*;
import com.example.LinkedInAutomations.config.security.TokenEncryptionUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostingWorker {

    private final ScheduleRepository scheduleRepository;
    private final DraftRepository draftRepository;
    private final PostAttemptRepository postAttemptRepository;
    private final LinkedInAccountRepository linkedInAccountRepository;
    private final LinkedInPublisher linkedInPublisher;

    private static final int MAX_RETRY = 3;

    @Scheduled(fixedRate = 60000)
    public void processSchedules() {

        List<Schedule> pending =
                scheduleRepository.findByStatusAndScheduledTimeBefore(
                        Schedule.ScheduleStatus.PENDING,
                        Instant.now());

        for (Schedule schedule : pending) {
            processSingleSchedule(schedule);
        }
    }

    @Transactional
    public void processSingleSchedule(Schedule schedule) {

        try {

            // 🔐 Idempotency guard
            if (schedule.getStatus() != Schedule.ScheduleStatus.PENDING) {
                return;
            }

            schedule.setStatus(Schedule.ScheduleStatus.PROCESSING);
            scheduleRepository.save(schedule);

            Draft draft = draftRepository.findById(schedule.getDraftId())
                    .orElseThrow(() -> new RuntimeException("Draft not found"));

            LinkedInAccount account =
                    linkedInAccountRepository.findByUserId(schedule.getUserId())
                            .orElseThrow(() -> new RuntimeException("LinkedIn not connected"));

            if (account.getExpiresAt() != null &&
                    account.getExpiresAt().isBefore(Instant.now())) {
                throw new RuntimeException("Access token expired");
            }

            // 🔓 Decrypt token before use
            String accessToken =
                    TokenEncryptionUtil.decrypt(account.getAccessToken());

            // 🚀 Publish via abstraction layer
            linkedInPublisher.publish(accessToken, draft.getGeneratedText());

            draft.setStatus("PUBLISHED");
            schedule.setStatus(Schedule.ScheduleStatus.POSTED);

            postAttemptRepository.save(
                    PostAttempt.builder()
                            .scheduleId(schedule.getId())
                            .draftId(draft.getId())
                            .status("SUCCESS")
                            .errorMessage(null)
                            .build()
            );

            log.info("Schedule {} posted successfully", schedule.getId());

        } catch (Exception e) {

            schedule.setRetryCount(schedule.getRetryCount() + 1);

            if (schedule.getRetryCount() >= MAX_RETRY) {
                schedule.setStatus(Schedule.ScheduleStatus.FAILED);
            } else {
                schedule.setStatus(Schedule.ScheduleStatus.PENDING);
            }

            postAttemptRepository.save(
                    PostAttempt.builder()
                            .scheduleId(schedule.getId())
                            .draftId(schedule.getDraftId())
                            .status("FAILED")
                            .errorMessage(e.getMessage())
                            .build()
            );

            log.error("Schedule {} failed", schedule.getId(), e);
        }

        scheduleRepository.save(schedule);
    }
}