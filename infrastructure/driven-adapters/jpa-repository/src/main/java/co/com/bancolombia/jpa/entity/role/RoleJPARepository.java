package co.com.bancolombia.jpa.entity.role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;


public interface RoleJPARepository extends CrudRepository<RoleEntity, Long>, QueryByExampleExecutor<RoleEntity> {
    Optional<RoleEntity> findByRoleKey(String roleKey);
}
