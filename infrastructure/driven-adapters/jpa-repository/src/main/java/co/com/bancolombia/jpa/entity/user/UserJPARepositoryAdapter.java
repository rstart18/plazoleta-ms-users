package co.com.bancolombia.jpa.entity.user;

import co.com.bancolombia.jpa.helper.AdapterOperations;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserJPARepositoryAdapter extends AdapterOperations<User, UserEntity, Long, UserJPARepository>
 implements UserRepository
{

    public UserJPARepositoryAdapter(UserJPARepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(this::toEntity);
    }
}
