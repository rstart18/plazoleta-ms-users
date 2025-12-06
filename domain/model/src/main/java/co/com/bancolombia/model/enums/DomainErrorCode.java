package co.com.bancolombia.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DomainErrorCode {
    // User errors
    USER_NOT_FOUND("USER_NOT_FOUND", "Usuario no encontrado"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "El usuario ya existe"),
    INVALID_USER_DATA("INVALID_USER_DATA", "Datos de usuario inválidos"),
    
    // Authentication & Authorization errors
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", "Credenciales inválidas"),
    UNAUTHORIZED_ACCESS("UNAUTHORIZED_ACCESS", "Acceso no autorizado"),
    ACCESS_DENIED("ACCESS_DENIED", "Acceso denegado"),
    ACCOUNT_LOCKED("ACCOUNT_LOCKED", "Cuenta bloqueada"),
    INVALID_TOKEN("INVALID_TOKEN", "Token inválido"),
    
    // Role errors
    ROLE_NOT_FOUND("ROLE_NOT_FOUND", "Rol no encontrado");

    private final String code;
    private final String message;
}