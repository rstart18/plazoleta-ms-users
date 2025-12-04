package co.com.bancolombia.usecase.loginattempt;

public interface LoginAttemptService {
    void validateLoginAttempt(String email);

    void recordFailedAttempt(String email);

    void resetAttempts(String email);
}
