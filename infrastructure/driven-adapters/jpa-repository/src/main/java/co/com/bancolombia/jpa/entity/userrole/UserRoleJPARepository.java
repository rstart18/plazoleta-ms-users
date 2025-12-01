package co.com.bancolombia.jpa.entity.userrole;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;


public interface UserRoleJPARepository extends CrudRepository<UserRoleEntity, Long>, QueryByExampleExecutor<UserRoleEntity> {
    List<UserRoleEntity> findByUserId(Long userId);
}
