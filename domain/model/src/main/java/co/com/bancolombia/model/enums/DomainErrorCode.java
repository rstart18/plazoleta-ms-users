package co.com.bancolombia.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DomainErrorCode {
    USER_NOT_FOUND("USER_NOT_FOUND", "Usuario no encontrado"),
    ROLE_NOT_FOUND("ROLE_NOT_FOUND", "Rol no encontrado"),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", "Credenciales inválidas"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "El usuario ya existe"),
    INVALID_USER_DATA("INVALID_USER_DATA", "Datos de usuario inválidos"),
    UNAUTHORIZED_ACCESS("UNAUTHORIZED_ACCESS", "Acceso no autorizado"),
    ACCOUNT_LOCKED("ACCOUNT_LOCKED", "Cuenta bloqueada"),
    INVALID_TOKEN("INVALID_TOKEN", "Token JWT inválido"),
    OWNER_NOT_AUTHORIZE_CREATE_EMPLOYEES("OWNER_NOT_AUTHORIZE_CREATE_EMPLOYEES", "Owner is not authorized to create employees for this restaurant");

    private final String code;
    private final String message;
}
