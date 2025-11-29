package co.com.bancolombia.api.config;

import co.com.bancolombia.api.dto.response.ErrorResponse;
import co.com.bancolombia.api.enums.ErrorCode;
import co.com.bancolombia.model.exception.BusinessException;
import io.micrometer.core.instrument.config.validate.ValidationException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.core.AuthenticationException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthenticationException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .code(ErrorCode.AUTHENTICATION_FAILED.getCode())
                .message(ErrorCode.AUTHENTICATION_FAILED.getDefaultMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .toList();

        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .code(ErrorCode.CONSTRAINT_VIOLATION.getCode())
                .message(ErrorCode.CONSTRAINT_VIOLATION.getDefaultMessage())
                .details(errors)
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .code(ErrorCode.VALIDATION_ERROR.getCode())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        log.error("Unexpected error: ", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .code(ErrorCode.INTERNAL_ERROR.getCode())
                        .message(ErrorCode.INTERNAL_ERROR.getDefaultMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}

