-- docker/mysql/init/01-schema.sql
CREATE DATABASE IF NOT EXISTS plazoleta_users;
USE plazoleta_users;

-- Tabla de usuarios
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'OWNER', 'EMPLOYEE', 'CUSTOMER') NOT NULL,
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Usuario admin para testing
INSERT INTO users (name, email, password, role, phone) VALUES
('Admin User', 'admin@plazoleta.com', '$2a$10$nzcA7oJSUuA.U5PUWfwQO.tnqe/KmarT1Peq6I4FgJCEKsRXLpRdS', 'ADMIN', '+1234567890');
-- Password: admin123 (BCrypt encoded)
