package co.com.bancolombia.jpa.entity.role;

import co.com.bancolombia.jpa.helper.AdapterOperations;
import co.com.bancolombia.model.role.Role;
import co.com.bancolombia.model.role.gateways.RoleRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleJPARepositoryAdapter extends AdapterOperations<Role, RoleEntity, Long, RoleJPARepository>
        implements RoleRepository {

    public RoleJPARepositoryAdapter(RoleJPARepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Role.class));
    }

    @Override
    public Optional<Role> findByKey(String key) {
        return repository.findRoleByRoleKey(key).map(this::toEntity);
    }
}
