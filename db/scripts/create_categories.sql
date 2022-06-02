CREATE TABLE IF NOT EXISTS categories(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

INSERT INTO categories(name) VALUES ('Urgent');

INSERT INTO categories(name) VALUES ('Daily');

INSERT INTO categories(name) VALUES ('Weekly');

INSERT INTO categories(name) VALUES ('Monthly');