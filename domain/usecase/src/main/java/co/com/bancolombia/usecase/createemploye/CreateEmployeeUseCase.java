package co.com.bancolombia.usecase.createemploye;

import co.com.bancolombia.model.enums.DomainErrorCode;
import co.com.bancolombia.model.enums.RoleEnum;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.restaurant.gateways.RestaurantGateway;
import co.com.bancolombia.model.role.Role;
import co.com.bancolombia.model.role.gateways.RoleRepository;
import co.com.bancolombia.model.security.gateways.PasswordEncoderGateway;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.model.userrole.UserRole;
import co.com.bancolombia.model.userrole.gateways.UserRoleRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateEmployeeUseCase implements CreateEmployeeService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoderGateway passwordEncoder;
    private final RestaurantGateway restaurantGateway;

    @Override
    public User createEmployee(User employee, Long restaurantId, Long ownerId, String authToken) {

        if (!restaurantGateway.isOwnerOfRestaurant(restaurantId, ownerId, authToken)) {
            throw new BusinessException(
                    DomainErrorCode.OWNER_NOT_AUTHORIZE_CREATE_EMPLOYEES.getCode(),
                    DomainErrorCode.OWNER_NOT_AUTHORIZE_CREATE_EMPLOYEES.getMessage());
        }

        if (userRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new BusinessException(
                    DomainErrorCode.USER_ALREADY_EXISTS.getCode(),
                    DomainErrorCode.USER_ALREADY_EXISTS.getMessage());
        }

        if (userRepository.findByIdentityDocument(employee.getIdentityDocument()).isPresent()) {
            throw new BusinessException(
                    DomainErrorCode.USER_ALREADY_EXISTS.getCode(),
                    DomainErrorCode.USER_ALREADY_EXISTS.getMessage());
        }

        Role employeeRole = roleRepository.findByRoleKey(RoleEnum.EMPLOYEE.getRoleKey())
                .orElseThrow(() -> new BusinessException(
                        DomainErrorCode.ROLE_NOT_FOUND.getCode(),
                        DomainErrorCode.ROLE_NOT_FOUND.getMessage()));

        String encodedPassword = passwordEncoder.encode(employee.getPassword());
        User employeeWithEncodedPassword = employee.toBuilder()
                .password(encodedPassword)
                .build();

        User savedEmployee = userRepository.create(employeeWithEncodedPassword);

        UserRole userRole = UserRole.builder()
                .user(savedEmployee)
                .role(employeeRole)
                .build();

        userRoleRepository.create(userRole);

        return savedEmployee;
    }
}
