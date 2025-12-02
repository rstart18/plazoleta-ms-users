package co.com.bancolombia.usecase.getuserrole;

import java.util.List;

public interface GetUserRoleService {
    List<String> getUserRoles(Long userId);
}
