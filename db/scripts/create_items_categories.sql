CREATE TABLE IF NOT EXISTS items_categories(
    id SERIAL PRIMARY KEY,
    item_id INT REFERENCES items(id),
    category_id INT REFERENCES categories(id)
);