package co.com.bancolombia.config;

import co.com.bancolombia.model.loginattempt.gateways.LoginAttemptRepository;
import co.com.bancolombia.usecase.loginattempt.LoginAttemptService;
import co.com.bancolombia.usecase.loginattempt.LoginAttemptUseCase;
import co.com.bancolombia.validator.OwnerValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.bancolombia.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                        classes = LoginAttemptUseCase.class)
        },
        useDefaultFilters = false)
public class UseCasesConfig {

    @Value("${login.max-attempts:3}")
    private Integer maxAttempts;

    @Value("${login.lockout-duration:15}")
    private Integer lockoutMinutes;

    @Bean
    public LoginAttemptService loginAttemptService(LoginAttemptRepository loginAttemptRepository) {
        return new LoginAttemptUseCase(loginAttemptRepository, maxAttempts, lockoutMinutes);
    }

    @Bean
    public OwnerValidator ownerValidator() {
        return new OwnerValidator();
    }
}
