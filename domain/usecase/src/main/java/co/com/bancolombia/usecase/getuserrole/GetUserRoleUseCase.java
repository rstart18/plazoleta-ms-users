package co.com.bancolombia.usecase.getuserrole;

import co.com.bancolombia.model.enums.DomainErrorCode;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.model.userrole.UserRole;
import co.com.bancolombia.model.userrole.gateways.UserRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetUserRoleUseCase implements GetUserRoleService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public List<String> getUserRoles(Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new BusinessException(DomainErrorCode.USER_NOT_FOUND);
        }

        List<UserRole> userRoles = userRoleRepository.findByUserId(userId);
        return userRoles.stream()
                .map(userRole -> userRole.getRole().getRoleKey())
                .toList();
    }
}
