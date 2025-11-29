package co.com.bancolombia.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DomainErrorCode {
    USER_NOT_FOUND("USER_NOT_FOUND", "Usuario no encontrado"),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", "Credenciales inválidas"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "El usuario ya existe"),
    INVALID_USER_DATA("INVALID_USER_DATA", "Datos de usuario inválidos"),
    UNAUTHORIZED_ACCESS("UNAUTHORIZED_ACCESS", "Acceso no autorizado");

    private final String code;
    private final String message;
}
