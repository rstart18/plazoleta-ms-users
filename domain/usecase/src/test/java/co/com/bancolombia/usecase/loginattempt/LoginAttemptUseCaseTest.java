package co.com.bancolombia.usecase.loginattempt;

import co.com.bancolombia.model.enums.DomainErrorCode;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.loginattempt.LoginAttempt;
import co.com.bancolombia.model.loginattempt.gateways.LoginAttemptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginAttemptUseCaseTest {

    @Mock
    private LoginAttemptRepository loginAttemptRepository;

    private LoginAttemptUseCase loginAttemptUseCase;

    private final String email = "test@example.com";

    @BeforeEach
    void setUp() {
        Integer maxAttempts = 3;
        Integer lockoutMinutes = 15;
        loginAttemptUseCase = new LoginAttemptUseCase(loginAttemptRepository, maxAttempts, lockoutMinutes);
    }

    @Test
    void shouldAllowLoginWhenNoAttempts() {
        // Given
        when(loginAttemptRepository.findByEmail(email)).thenReturn(null);

        // When & Then
        assertDoesNotThrow(() -> loginAttemptUseCase.validateLoginAttempt(email));

        verify(loginAttemptRepository).findByEmail(email);
    }

    @Test
    void shouldAllowLoginWhenNotLocked() {
        // Given
        LoginAttempt attempt = LoginAttempt.builder()
                .email(email)
                .attempts(2)
                .isLocked(false)
                .build();

        when(loginAttemptRepository.findByEmail(email)).thenReturn(attempt);

        // When & Then
        assertDoesNotThrow(() -> loginAttemptUseCase.validateLoginAttempt(email));

        verify(loginAttemptRepository).findByEmail(email);
    }

    @Test
    void shouldThrowExceptionWhenAccountLocked() {
        // Given
        LoginAttempt attempt = LoginAttempt.builder()
                .email(email)
                .attempts(3)
                .isLocked(true)
                .lockedUntil(LocalDateTime.now().plusMinutes(10))
                .build();

        when(loginAttemptRepository.findByEmail(email)).thenReturn(attempt);

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> loginAttemptUseCase.validateLoginAttempt(email));

        assertEquals(DomainErrorCode.ACCOUNT_LOCKED.getCode(), exception.getCode());
        assertEquals(DomainErrorCode.ACCOUNT_LOCKED.getMessage(), exception.getMessage());

        verify(loginAttemptRepository).findByEmail(email);
    }

    @Test
    void shouldAllowLoginWhenLockExpired() {
        // Given
        LoginAttempt attempt = LoginAttempt.builder()
                .email(email)
                .attempts(3)
                .isLocked(true)
                .lockedUntil(LocalDateTime.now().minusMinutes(5)) // Expirado
                .build();

        when(loginAttemptRepository.findByEmail(email)).thenReturn(attempt);

        // When & Then
        assertDoesNotThrow(() -> loginAttemptUseCase.validateLoginAttempt(email));

        verify(loginAttemptRepository).findByEmail(email);
    }

    @Test
    void shouldCreateFirstFailedAttempt() {
        // Given
        when(loginAttemptRepository.findByEmail(email)).thenReturn(null);
        when(loginAttemptRepository.save(any(LoginAttempt.class))).thenReturn(any(LoginAttempt.class));

        // When
        loginAttemptUseCase.recordFailedAttempt(email);

        // Then
        verify(loginAttemptRepository).findByEmail(email);
        verify(loginAttemptRepository).save(argThat(attempt ->
                attempt.getEmail().equals(email) &&
                        attempt.getAttempts().equals(1) &&
                        !attempt.getIsLocked()
        ));
    }

    @Test
    void shouldIncrementFailedAttempts() {
        // Given
        LoginAttempt existingAttempt = LoginAttempt.builder()
                .email(email)
                .attempts(1)
                .isLocked(false)
                .build();

        when(loginAttemptRepository.findByEmail(email)).thenReturn(existingAttempt);
        when(loginAttemptRepository.save(any(LoginAttempt.class))).thenReturn(any(LoginAttempt.class));

        // When
        loginAttemptUseCase.recordFailedAttempt(email);

        // Then
        verify(loginAttemptRepository).save(argThat(attempt ->
                attempt.getAttempts().equals(2) &&
                        !attempt.getIsLocked()
        ));
    }

    @Test
    void shouldLockAccountAfterMaxAttempts() {
        // Given
        LoginAttempt existingAttempt = LoginAttempt.builder()
                .email(email)
                .attempts(2)
                .isLocked(false)
                .build();

        when(loginAttemptRepository.findByEmail(email)).thenReturn(existingAttempt);
        when(loginAttemptRepository.save(any(LoginAttempt.class))).thenReturn(any(LoginAttempt.class));

        // When
        loginAttemptUseCase.recordFailedAttempt(email);

        // Then
        verify(loginAttemptRepository).save(argThat(attempt ->
                attempt.getAttempts().equals(3) &&
                        attempt.getIsLocked() &&
                        attempt.getLockedUntil() != null
        ));
    }

    @Test
    void shouldResetAttempts() {
        // When
        loginAttemptUseCase.resetAttempts(email);

        // Then
        verify(loginAttemptRepository).deleteByEmail(email);
    }
}
