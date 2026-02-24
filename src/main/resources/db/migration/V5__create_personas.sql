
CREATE TABLE personas (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    tone VARCHAR(255) NOT NULL,
    writing_style TEXT,
    dos TEXT,
    donts TEXT,
    experience_summary TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_persona_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);