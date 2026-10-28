-- Run in MySQL:   mysql -u root -p < schema.sql
CREATE DATABASE IF NOT EXISTS college CHARACTER SET utf8mb4;
USE college;

CREATE TABLE IF NOT EXISTS users (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    email         VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(200) NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS students (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    email   VARCHAR(150) NOT NULL,
    course  VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS accounts (          -- used by the transaction demo
    acc_no  INT PRIMARY KEY,
    holder  VARCHAR(100),
    balance DECIMAL(10,2)
);

INSERT INTO students(name, email, course) VALUES
    ('Asha',  'asha@example.com',  'MCA'),
    ('Ravi',  'ravi@example.com',  'M.Tech'),
    ('Meena', 'meena@example.com', 'MCA');

INSERT INTO accounts VALUES (101, 'Asha', 5000.00), (102, 'Ravi', 3000.00);

DELIMITER $$
CREATE PROCEDURE get_student(IN p_id INT, OUT p_name VARCHAR(100))
BEGIN
    SELECT name INTO p_name FROM students WHERE id = p_id;
END$$
DELIMITER ;
