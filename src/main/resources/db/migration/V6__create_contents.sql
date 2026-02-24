CREATE TABLE contents (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    persona_id UUID NOT NULL,
    content_type VARCHAR(50) NOT NULL,
    topic TEXT,
    generated_text TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);