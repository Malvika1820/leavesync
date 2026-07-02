CREATE DATABASE IF NOT EXISTS leavesync_db;
USE leavesync_db;

CREATE TABLE IF NOT EXISTS students (
    student_id     INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    roll_no        VARCHAR(30) NOT NULL UNIQUE,
    email          VARCHAR(100) NOT NULL,
    leave_balance  INT NOT NULL DEFAULT 12
);

CREATE TABLE IF NOT EXISTS leave_requests (
    leave_id    INT AUTO_INCREMENT PRIMARY KEY,
    student_id  INT NOT NULL,
    from_date   DATE NOT NULL,
    to_date     DATE NOT NULL,
    reason      VARCHAR(255),
    status      VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (student_id) REFERENCES students(student_id)
);

-- Sample seed data
INSERT INTO students (name, roll_no, email, leave_balance) VALUES
('Malvika Doddamani', '20231CSD0155', 'malvikadoddamani@gmail.com', 12),
('Razia Sultana', '20241CSD3001', 'razia.sultana@example.com', 12);
