package co.com.bancolombia.model.user.gateways;

import co.com.bancolombia.model.user.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);

    Optional<User> findByIdentityDocument(String identityDocument);

    User create(User user);
}
