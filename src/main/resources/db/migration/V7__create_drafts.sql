CREATE TABLE drafts (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    persona_id UUID NOT NULL,
    topic TEXT NOT NULL,
    style_type VARCHAR(50) NOT NULL,
    generated_text TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);