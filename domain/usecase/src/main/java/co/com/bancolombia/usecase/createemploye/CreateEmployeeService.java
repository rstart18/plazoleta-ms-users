package co.com.bancolombia.usecase.createemploye;

import co.com.bancolombia.model.user.User;

public interface CreateEmployeeService {
    User createEmployee(User employee, Long restaurantId, Long OwnerId, String authToken);
}
