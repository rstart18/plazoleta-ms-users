package co.com.bancolombia.jpa.entity.loginattempt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "login_attempts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginAttemptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)  // Índice único
    private String email;

    @Column(name = "attempts")
    private Integer attempts;

    @Column(name = "last_attempt")
    private LocalDateTime lastAttempt;

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;

    @Column(name = "is_locked")
    private Boolean isLocked;
}

