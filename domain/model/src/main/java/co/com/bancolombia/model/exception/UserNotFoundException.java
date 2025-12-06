package co.com.bancolombia.model.exception;

import co.com.bancolombia.model.enums.DomainErrorCode;

public class UserNotFoundException extends BusinessException {
    private static final long serialVersionUID = 1003L;
    
    public UserNotFoundException() {
        super(DomainErrorCode.USER_NOT_FOUND);
    }
}