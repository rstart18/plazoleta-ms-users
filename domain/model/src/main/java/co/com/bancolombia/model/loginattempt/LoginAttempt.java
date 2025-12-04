package co.com.bancolombia.model.loginattempt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoginAttempt {
    private Long id;
    private String email;
    private Integer attempts;
    private LocalDateTime lastAttempt;
    private LocalDateTime lockedUntil;
    private Boolean isLocked;
}
