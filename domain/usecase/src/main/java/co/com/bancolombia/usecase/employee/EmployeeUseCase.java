package co.com.bancolombia.usecase.employee;

import co.com.bancolombia.model.employeerestaurant.EmployeeRestaurant;
import co.com.bancolombia.model.employeerestaurant.gateways.EmployeeRestaurantRepository;
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

import java.util.List;

@RequiredArgsConstructor
public class EmployeeUseCase implements EmployeeService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final EmployeeRestaurantRepository employeeRestaurantRepository;
    private final PasswordEncoderGateway passwordEncoder;

    @Override
    public User createEmployee(User employee, Long restaurantId, String userRole, Long ownerId) {
        RoleValidator.validateOwnerRole(userRole);

        if (userRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new BusinessException(DomainErrorCode.EMAIL_ALREADY_EXISTS);
        }

        if (userRepository.findByIdentityDocument(employee.getIdentityDocument()).isPresent()) {
            throw new BusinessException(DomainErrorCode.DOCUMENT_ALREADY_EXISTS);
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

        EmployeeRestaurant employeeRestaurant = EmployeeRestaurant.builder()
                .employeeId(savedUser.getId())
                .restaurantId(restaurantId)
                .build();

        employeeRestaurantRepository.create(employeeRestaurant);

        return savedUser;
    }

    @Override
    public Long getEmployeeRestaurant(Long employeeId) {
        User user = userRepository.findById(employeeId);
        if (user == null) {
            throw new BusinessException(DomainErrorCode.EMPLOYEE_NOT_FOUND);
        }

        List<UserRole> userRoles = userRoleRepository.findByUserId(employeeId);
        boolean isEmployee = userRoles.stream()
                .anyMatch(userRole -> RoleEnum.EMPLOYEE.getRoleKey().equals(userRole.getRole().getRoleKey()));

        if (!isEmployee) {
            throw new BusinessException(DomainErrorCode.USER_NOT_EMPLOYEE);
        }

        EmployeeRestaurant employeeRestaurant = employeeRestaurantRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new BusinessException(DomainErrorCode.RESTAURANT_NOT_ASSIGNED));

        return employeeRestaurant.getRestaurantId();
    }
}