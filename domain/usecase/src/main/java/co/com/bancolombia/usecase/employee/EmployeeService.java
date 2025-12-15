package co.com.bancolombia.usecase.employee;

import co.com.bancolombia.model.user.User;

public interface EmployeeService {
    User createEmployee(User employee, Long restaurantId, String userRole, Long ownerId);
    Long getEmployeeRestaurant(Long employeeId);
}