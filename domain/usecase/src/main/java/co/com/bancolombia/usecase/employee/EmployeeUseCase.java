package co.com.bancolombia.usecase.employee;

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
import co.com.bancolombia.usecase.validator.RoleValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmployeeUseCase implements EmployeeService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoderGateway passwordEncoder;

    @Override
    public User createEmployee(User employee, Long restaurantId, String userRole, Long ownerId) {
        RoleValidator.validateOwnerRole(userRole);

        if (userRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new BusinessException(DomainErrorCode.USER_ALREADY_EXISTS);
        }

        if (userRepository.findByIdentityDocument(employee.getIdentityDocument()).isPresent()) {
            throw new BusinessException(DomainErrorCode.USER_ALREADY_EXISTS);
        }

        Role employeeRole = roleRepository.findByRoleKey(RoleEnum.EMPLOYEE.getRoleKey())
                .orElseThrow(() -> new BusinessException(DomainErrorCode.ROLE_NOT_FOUND));

        String encodedPassword = passwordEncoder.encode(employee.getPassword());
        User employeeWithEncodedPassword = employee.toBuilder()
                .password(encodedPassword)
                .build();

        User savedUser = userRepository.create(employeeWithEncodedPassword);

        UserRole employeeUserRole = UserRole.builder()
                .user(savedUser)
                .role(employeeRole)
                .build();

        userRoleRepository.create(employeeUserRole);

        return savedUser;
    }
}