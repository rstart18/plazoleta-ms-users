package co.com.bancolombia.jpa.entity.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;

public interface UserJPARepository extends CrudRepository<UserEntity, Long>, QueryByExampleExecutor<UserEntity> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByIdentityDocument(String identityDocument);
}
