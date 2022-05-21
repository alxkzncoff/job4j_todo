CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255),
    created TIMESTAMP,
    done BOOLEAN
);