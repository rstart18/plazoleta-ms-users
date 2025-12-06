package co.com.bancolombia.usecase.createowner;


import co.com.bancolombia.model.enums.DomainErrorCode;
import co.com.bancolombia.model.enums.RoleEnum;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.role.Role;
import co.com.bancolombia.model.role.gateways.RoleRepository;
import co.com.bancolombia.model.security.gateways.PasswordEncoderGateway;
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
    private final PasswordEncoderGateway passwordEncoder;

    @Override
    public User createOwner(User owner) {
        ownerValidator.validateCreateOwner(owner);

        if (userRepository.findByEmail(owner.getEmail()).isPresent()) {
            throw new BusinessException(DomainErrorCode.USER_ALREADY_EXISTS);
        }

        if (userRepository.findByIdentityDocument(owner.getIdentityDocument()).isPresent()) {
            throw new BusinessException(DomainErrorCode.USER_ALREADY_EXISTS);
        }

        Role ownerRole = roleRepository.findByRoleKey(RoleEnum.OWNER.getRoleKey())
                .orElseThrow(() -> new BusinessException(DomainErrorCode.ROLE_NOT_FOUND));

        String encodedPassword = passwordEncoder.encode(owner.getPassword());
        User ownerWithEncodedPassword = owner.toBuilder()
                .password(encodedPassword)
                .build();

        User savedUser = userRepository.create(ownerWithEncodedPassword);

        UserRole userRole = UserRole.builder()
                .user(savedUser)
                .role(ownerRole)
                .build();

        userRoleRepository.create(userRole);

        return savedUser;
    }
}