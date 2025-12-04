package co.com.bancolombia.jpa.entity.loginattempt;

import co.com.bancolombia.jpa.helper.AdapterOperations;
import co.com.bancolombia.model.loginattempt.LoginAttempt;
import co.com.bancolombia.model.loginattempt.gateways.LoginAttemptRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LoginAttemptJPARepositoryAdapter extends AdapterOperations<LoginAttempt, LoginAttemptEntity, Long, LoginAttemptJPARepository>
        implements LoginAttemptRepository {

    public LoginAttemptJPARepositoryAdapter(LoginAttemptJPARepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, LoginAttempt.class));
    }

    @Override
    public LoginAttempt findByEmail(String email) {
        return repository.findByEmail(email)
                .map(entity -> mapper.map(entity, LoginAttempt.class))
                .orElse(null);
    }

    @Override
    @Transactional
    public LoginAttempt save(LoginAttempt attempt) {
        LoginAttemptEntity entity = mapper.map(attempt, LoginAttemptEntity.class);
        LoginAttemptEntity saved = repository.save(entity);
        return mapper.map(saved, LoginAttempt.class);
    }

    @Override
    @Transactional
    public void deleteByEmail(String email) {
        repository.deleteByEmail(email);
    }
}
