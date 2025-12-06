package co.com.bancolombia.model.exception;

import co.com.bancolombia.model.enums.DomainErrorCode;

public class InvalidCredentialsException extends BusinessException {
    private static final long serialVersionUID = 1002L;
    
    public InvalidCredentialsException() {
        super(DomainErrorCode.INVALID_CREDENTIALS.getCode(),
                DomainErrorCode.INVALID_CREDENTIALS.getMessage());
    }
}