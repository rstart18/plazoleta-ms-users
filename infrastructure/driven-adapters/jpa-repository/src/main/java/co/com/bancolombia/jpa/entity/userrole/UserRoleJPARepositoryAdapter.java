package co.com.bancolombia.jpa.entity.userrole;

import co.com.bancolombia.jpa.helper.AdapterOperations;
import co.com.bancolombia.model.userrole.UserRole;
import co.com.bancolombia.model.userrole.gateways.UserRoleRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

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
}
