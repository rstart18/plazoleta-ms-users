package co.com.bancolombia.model.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Employee {
    private Long id;
    private String name;
    private String lastName;
    private String identityDocument;
    private String phone;
    private String email;
    private String password;
    private Long restaurantId;
    private String role;
}
