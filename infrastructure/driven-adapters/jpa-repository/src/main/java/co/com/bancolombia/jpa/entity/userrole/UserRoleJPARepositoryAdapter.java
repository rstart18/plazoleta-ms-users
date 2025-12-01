package co.com.bancolombia.jpa.entity.userrole;

import co.com.bancolombia.jpa.helper.AdapterOperations;
import co.com.bancolombia.model.userrole.UserRole;
import co.com.bancolombia.model.userrole.gateways.UserRoleRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRoleJPARepositoryAdapter extends AdapterOperations<UserRole, UserRoleEntity, Long, UserRoleJPARepository>
        implements UserRoleRepository {

    public UserRoleJPARepositoryAdapter(UserRoleJPARepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, UserRole.class));
    }

    @Override
    public UserRole create(UserRole userRole) {
        return toEntity(repository.save(toData(userRole)));
    }

    @Override
    public List<UserRole> findByUserId(Long userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
