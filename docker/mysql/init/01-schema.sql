-- docker/mysql/init/01-schema.sql
CREATE DATABASE IF NOT EXISTS plazoleta_users;
USE plazoleta_users;

-- Tabla de usuarios
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    identity_document VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    birth_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de roles
CREATE TABLE roles (
    id BIGINT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    role_key VARCHAR(20) NOT NULL UNIQUE
);

-- Tabla de relación usuario-rol
CREATE TABLE user_roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_role (user_id, role_id)
);

-- Insertar roles con IDs constantes
INSERT INTO roles (id, name, role_key) VALUES
(1, 'Admin', 'ADMIN'),
(2, 'Owner', 'OWNER');

-- Usuario admin para testing
INSERT INTO users (first_name, last_name, identity_document, email, password, phone, birth_date) VALUES
('Admin', 'User', '12345678', 'admin@plazoleta.com', '$2a$10$nzcA7oJSUuA.U5PUWfwQO.tnqe/KmarT1Peq6I4FgJCEKsRXLpRdS', '+1234567890', '1990-01-01');

-- Asignar rol ADMIN al usuario admin
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1);

-- Password: admin123 (BCrypt encoded)
