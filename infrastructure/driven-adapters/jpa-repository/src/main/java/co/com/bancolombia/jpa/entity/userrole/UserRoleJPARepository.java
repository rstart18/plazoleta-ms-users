package co.com.bancolombia.jpa.entity.userrole;

import co.com.bancolombia.jpa.entity.role.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;


public interface UserRoleJPARepository extends CrudRepository<UserRoleEntity, Long>, QueryByExampleExecutor<UserRoleEntity> {
}
