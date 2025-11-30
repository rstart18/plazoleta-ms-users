package co.com.bancolombia.validator;

import co.com.bancolombia.model.enums.DomainErrorCode;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.user.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class OwnerValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\+?[0-9]{1,13}$"
    );

    private static final Pattern DOCUMENT_PATTERN = Pattern.compile(
            "^\\d+$"
    );

    public void validateCreateOwner(User user) {
        validateEmail(user.getEmail());
        validatePhone(user.getPhone());
        validateDocumentId(user.getIdentityDocument());
        validateAge(user.getBirthDate());
    }

    public void validateAge(LocalDate birthDate) {
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < 18) {
            throw new BusinessException(DomainErrorCode.INVALID_USER_DATA.getCode(), "User must be at least 18 years old");
        }
    }

    public void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new BusinessException(DomainErrorCode.INVALID_USER_DATA.getCode(), "Invalid email format");
        }
    }

    public void validatePhone(String phone) {
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
            throw new BusinessException(DomainErrorCode.INVALID_USER_DATA.getCode(), "Phone must contain maximum 13 characters and can include + symbol");
        }
    }

    public void validateDocumentId(String documentId) {
        if (documentId == null || !DOCUMENT_PATTERN.matcher(documentId).matches()) {
            throw new BusinessException(DomainErrorCode.INVALID_USER_DATA.getCode(), "Document ID must be numeric only");
        }
    }
}