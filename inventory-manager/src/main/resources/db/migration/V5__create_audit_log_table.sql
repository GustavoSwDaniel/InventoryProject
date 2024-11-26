CREATE TABLE audit_log (
    id SERIAL PRIMARY KEY,
    action VARCHAR(255) NOT NULL,
    data TEXT,
    user_id INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);