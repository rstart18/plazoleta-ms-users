package co.com.bancolombia.model.exception;

import co.com.bancolombia.model.enums.DomainErrorCode;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String email) {
        super(DomainErrorCode.USER_NOT_FOUND.getCode(),
                DomainErrorCode.USER_NOT_FOUND.getMessage() + ": " + email);
    }
}