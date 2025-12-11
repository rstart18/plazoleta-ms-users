package co.com.bancolombia.usecase.client;

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
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientUseCase implements ClientService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoderGateway passwordEncoder;

    public User createClient(User client) {
        if (userRepository.findByEmail(client.getEmail()).isPresent()) {
            throw new BusinessException(DomainErrorCode.USER_ALREADY_EXISTS);
        }

        if (userRepository.findByIdentityDocument(client.getIdentityDocument()).isPresent()) {
            throw new BusinessException(DomainErrorCode.USER_ALREADY_EXISTS);
        }

        Role ownerRole = roleRepository.findByRoleKey(RoleEnum.CLIENT.getRoleKey())
                .orElseThrow(() -> new BusinessException(DomainErrorCode.ROLE_NOT_FOUND));

        String encodedPassword = passwordEncoder.encode(client.getPassword());
        User clientWithEncodedPassword = client.toBuilder()
                .password(encodedPassword)
                .build();

        User savedUser = userRepository.create(clientWithEncodedPassword);

        UserRole userRole = UserRole.builder()
                .user(savedUser)
                .role(ownerRole)
                .build();

        userRoleRepository.create(userRole);

        return savedUser;
    }
}
