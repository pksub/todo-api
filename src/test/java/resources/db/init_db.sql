CREATE TABLE IF NOT EXISTS member (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    password TEXT NOT NULL,
    phone TEXT, 
    role TEXT
);


INSERT INTO member (username, password, phone, role)
VALUES ('admin', 'password', NULL, 'ADMIN');

