package co.com.bancolombia.jpa.entity.loginattempt;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;


public interface LoginAttemptJPARepository extends CrudRepository<LoginAttemptEntity, Long>, QueryByExampleExecutor<LoginAttemptEntity> {
    Optional<LoginAttemptEntity> findByEmail(String email);

    void deleteByEmail(String email);
}
