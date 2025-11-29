package co.com.bancolombia.model.exception;

import co.com.bancolombia.model.enums.DomainErrorCode;

public class InvalidCredentialsException extends BusinessException {
    public InvalidCredentialsException() {
        super(DomainErrorCode.INVALID_CREDENTIALS.getCode(),
                DomainErrorCode.INVALID_CREDENTIALS.getMessage());
    }
}