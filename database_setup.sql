-- Create database (run this separately in pgAdmin)
-- CREATE DATABASE library_db;

-- Connect to library_db, then run the following:

CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    is_borrowed BOOLEAN DEFAULT FALSE
);

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE borrowing_records (
    record_id SERIAL PRIMARY KEY,
    book_id INTEGER REFERENCES books(id),
    user_id INTEGER REFERENCES users(user_id),
    borrow_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    return_date TIMESTAMP,
    is_returned BOOLEAN DEFAULT FALSE
);

-- Sample data
INSERT INTO books (title, author, is_borrowed) VALUES 
('The Hobbit', 'J.R.R. Tolkien', false),
('1984', 'George Orwell', false),
('Clean Code', 'Robert C. Martin', false);