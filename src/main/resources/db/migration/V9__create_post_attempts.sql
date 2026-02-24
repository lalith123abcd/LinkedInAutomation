CREATE TABLE post_attempts (
    id UUID PRIMARY KEY,
    schedule_id UUID,
    draft_id UUID,
    status VARCHAR(50),
    error_message TEXT,
    attempted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_post_attempt_schedule
ON post_attempts(schedule_id);

CREATE INDEX idx_post_attempt_draft
ON post_attempts(draft_id);