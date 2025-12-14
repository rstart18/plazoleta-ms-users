package co.com.bancolombia.usecase.userrole;

import java.util.List;

public interface UserRoleService {
    List<String> getUserRoles(Long userId, String userRole);
}
