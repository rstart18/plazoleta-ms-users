package co.com.bancolombia.model.userrole.gateways;

import co.com.bancolombia.model.userrole.UserRole;

import java.util.List;

public interface UserRoleRepository {
    UserRole create(UserRole userRole);

    List<UserRole> findByUserId(Long userId);
}
