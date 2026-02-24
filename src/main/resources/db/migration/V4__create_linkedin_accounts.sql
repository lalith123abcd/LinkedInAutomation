CREATE TABLE linkedin_accounts (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    linkedin_user_id VARCHAR(255) NOT NULL UNIQUE,
    access_token VARCHAR(2000) NOT NULL,
    refresh_token VARCHAR(2000),
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_linkedin_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);