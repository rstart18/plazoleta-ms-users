package co.com.bancolombia.model.employeerestaurant.gateways;

import co.com.bancolombia.model.employeerestaurant.EmployeeRestaurant;

import java.util.Optional;

public interface EmployeeRestaurantRepository {
    Optional<EmployeeRestaurant> findByEmployeeId(Long employeeId);
    EmployeeRestaurant create(EmployeeRestaurant employeeRestaurant);
}