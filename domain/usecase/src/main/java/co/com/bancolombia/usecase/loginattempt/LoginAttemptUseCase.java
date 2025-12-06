package co.com.bancolombia.usecase.loginattempt;

import co.com.bancolombia.model.enums.DomainErrorCode;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.loginattempt.LoginAttempt;
import co.com.bancolombia.model.loginattempt.gateways.LoginAttemptRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class LoginAttemptUseCase implements LoginAttemptService {

    private final LoginAttemptRepository loginAttemptRepository;

    private final Integer maxAttempts;
    private final Integer lockoutMinutes;

    @Override
    public void validateLoginAttempt(String email) {
        LoginAttempt attempt = loginAttemptRepository.findByEmail(email);

        if (attempt != null && attempt.getIsLocked() &&
                attempt.getLockedUntil().isAfter(LocalDateTime.now())) {
            throw new BusinessException(DomainErrorCode.ACCOUNT_LOCKED);
        }
    }

    @Override
    public void recordFailedAttempt(String email) {
        LoginAttempt attempt = loginAttemptRepository.findByEmail(email);

        if (attempt == null) {
            attempt = LoginAttempt.builder()
                    .email(email)
                    .attempts(1)
                    .lastAttempt(LocalDateTime.now())
                    .isLocked(false)
                    .build();
        } else {
            attempt.setAttempts(attempt.getAttempts() + 1);
            attempt.setLastAttempt(LocalDateTime.now());
        }

        if (attempt.getAttempts() >= maxAttempts) {
            attempt.setIsLocked(true);
            attempt.setLockedUntil(LocalDateTime.now().plusMinutes(lockoutMinutes));
        }

        loginAttemptRepository.save(attempt);
    }

    @Override
    public void resetAttempts(String email) {
        loginAttemptRepository.deleteByEmail(email);
    }
}
