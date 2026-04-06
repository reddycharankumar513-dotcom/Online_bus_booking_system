-- ============================================
--   goBus - Bus Ticket Booking System
--   Run this file first in MySQL Workbench
-- ============================================

CREATE DATABASE IF NOT EXISTS gobus1;
USE gobus1;

-- 1. Users Table
CREATE TABLE users (
    user_id  INT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(100),
    email    VARCHAR(100) UNIQUE,
    phone    VARCHAR(15),
    password VARCHAR(100)
);

-- 2. Admin Table (separate from users)
CREATE TABLE admin (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    password VARCHAR(100)
);

-- 3. Buses Table
CREATE TABLE buses (
    bus_id          INT PRIMARY KEY AUTO_INCREMENT,
    bus_name        VARCHAR(100),
    bus_number      VARCHAR(50),
    from_location   VARCHAR(100),
    to_location     VARCHAR(100),
    travel_date     VARCHAR(50),
    departure_time  VARCHAR(50),
    total_seats     INT,
    available_seats INT,
    price           DOUBLE
);

-- 4. Bookings Table
CREATE TABLE bookings (
    booking_id   INT PRIMARY KEY AUTO_INCREMENT,
    user_id      INT,
    bus_id       INT,
    num_seats    INT,
    total_price  DOUBLE,
    booking_date VARCHAR(50)
);

-- ============================================
-- Sample Data
-- ============================================

-- Admin (stored in admin table)
INSERT INTO admin (username, password) VALUES ('admin', 'admin123');

-- Sample Users
INSERT INTO users (name, email, phone, password) VALUES
('Ravi Kumar',  'ravi@gmail.com',  '9876543210', 'ravi123'),
('Priya Singh', 'priya@gmail.com', '9876543211', 'priya123');

-- Sample Buses
INSERT INTO buses (bus_name, bus_number, from_location, to_location, travel_date, departure_time, total_seats, available_seats, price) VALUES
('Royal Travels',   'TN01AB1234', 'Chennai',   'Bangalore',  '2026-05-10', '06:00 AM', 40, 40, 350.00),
('KPN Travels',     'TN02CD5678', 'Chennai',   'Coimbatore', '2026-05-10', '08:00 PM', 40, 40, 450.00),
('SRS Travels',     'KA03EF9012', 'Bangalore', 'Hyderabad',  '2026-05-11', '09:00 PM', 45, 45, 600.00),
('Orange Travels',  'AP04GH3456', 'Hyderabad', 'Chennai',    '2026-05-11', '07:00 PM', 40, 40, 500.00),
('VRL Travels',     'KA05IJ7890', 'Bangalore', 'Mumbai',     '2026-05-12', '05:00 PM', 50, 50, 900.00),
('Parveen Travels', 'TN06KL2345', 'Chennai',   'Madurai',    '2026-05-12', '10:00 PM', 40, 40, 300.00);
