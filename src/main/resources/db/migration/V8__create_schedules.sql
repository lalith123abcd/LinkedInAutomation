CREATE TABLE schedules (
    id UUID PRIMARY KEY,
    draft_id UUID NOT NULL UNIQUE,
    user_id UUID NOT NULL,
    scheduled_time TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    retry_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);