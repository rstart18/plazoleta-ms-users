package co.com.bancolombia.model.employeerestaurant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class EmployeeRestaurant {
    private Long id;
    private Long employeeId;
    private Long restaurantId;
}