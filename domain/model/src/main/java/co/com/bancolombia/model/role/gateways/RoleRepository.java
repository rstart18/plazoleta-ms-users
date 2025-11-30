package co.com.bancolombia.model.role.gateways;

import co.com.bancolombia.model.role.Role;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByKey(String key);
}
