package com.example.LinkedInAutomations.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ScheduleRequestDTO {

    private UUID draftId;
    private Instant scheduledTime; // Use ISO format in Postman
}