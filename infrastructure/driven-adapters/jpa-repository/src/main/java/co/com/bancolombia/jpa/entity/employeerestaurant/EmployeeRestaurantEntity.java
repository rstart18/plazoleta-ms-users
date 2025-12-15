package co.com.bancolombia.jpa.entity.employeerestaurant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee_restaurant")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRestaurantEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;
    
    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;
}