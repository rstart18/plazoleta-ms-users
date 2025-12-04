package co.com.bancolombia.model.loginattempt.gateways;

import co.com.bancolombia.model.loginattempt.LoginAttempt;

public interface LoginAttemptRepository {
    LoginAttempt findByEmail(String email);

    LoginAttempt save(LoginAttempt attempt);

    void deleteByEmail(String email);
}
