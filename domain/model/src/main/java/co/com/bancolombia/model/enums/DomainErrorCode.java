package co.com.bancolombia.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DomainErrorCode {
    // User errors
    USER_NOT_FOUND("USER_NOT_FOUND", "Usuario no encontrado"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "El usuario ya existe"),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS", "Ya existe un usuario con este correo electrónico"),
    DOCUMENT_ALREADY_EXISTS("DOCUMENT_ALREADY_EXISTS", "Ya existe un usuario con este número de identificación"),
    INVALID_USER_DATA("INVALID_USER_DATA", "Datos de usuario inválidos"),
    INVALID_EMAIL("INVALID_EMAIL", "Email inválido"),
    INVALID_PHONE("INVALID_PHONE", "Teléfono inválido"),
    INVALID_DOCUMENT("INVALID_DOCUMENT", "Documento de identidad inválido"),
    INVALID_AGE("INVALID_AGE", "Edad inválida, debe ser mayor de 18 años"),
    
    // Authentication & Authorization errors
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", "Credenciales inválidas"),
    UNAUTHORIZED_ACCESS("UNAUTHORIZED_ACCESS", "Acceso no autorizado"),
    ACCESS_DENIED("ACCESS_DENIED", "Acceso denegado"),
    ACCOUNT_LOCKED("ACCOUNT_LOCKED", "Cuenta bloqueada"),
    INVALID_TOKEN("INVALID_TOKEN", "Token inválido"),
    
    // Role errors
    ROLE_NOT_FOUND("ROLE_NOT_FOUND", "Rol no encontrado"),
    INSUFFICIENT_PERMISSIONS("INSUFFICIENT_PERMISSIONS", "Permisos insuficientes"),
    
    // Employee errors
    EMPLOYEE_NOT_FOUND("EMPLOYEE_NOT_FOUND", "Empleado no encontrado"),
    USER_NOT_EMPLOYEE("USER_NOT_EMPLOYEE", "El usuario no es empleado"),
    RESTAURANT_NOT_ASSIGNED("RESTAURANT_NOT_ASSIGNED", "Empleado no tiene restaurante asignado");

    private final String code;
    private final String message;
}