package co.com.bancolombia.usecase.createowner;


import co.com.bancolombia.model.enums.DomainErrorCode;
import co.com.bancolombia.model.enums.RoleEnum;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.role.Role;
import co.com.bancolombia.model.role.gateways.RoleRepository;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.model.userrole.UserRole;
import co.com.bancolombia.model.userrole.gateways.UserRoleRepository;
import co.com.bancolombia.validator.OwnerValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateOwnerUseCase implements CreateOwnerService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final OwnerValidator ownerValidator;

    @Override
    public User execute(User owner) {
        ownerValidator.validateCreateOwner(owner);

        if (userRepository.findByEmail(owner.getEmail()).isPresent()) {
            throw new BusinessException(DomainErrorCode.USER_ALREADY_EXISTS.getCode(), "Email already exists");
        }

        Role ownerRole = roleRepository.findByKey(RoleEnum.OWNER.getRoleKey())
                .orElseThrow(() -> new BusinessException(DomainErrorCode.ROLE_NOT_FOUND.getCode(), "Owner role not found"));

        User savedUser = userRepository.create(owner);

        UserRole userRole = UserRole.builder()
                .userId(savedUser.getId())
                .roleId(ownerRole.getId())
                .build();

        userRoleRepository.create(userRole);

        return savedUser;
    }
}