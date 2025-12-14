package co.com.bancolombia.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateEmployeeRequest {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    @NotBlank(message = "Identity document is required")
    private String identityDocument;
    
    @NotBlank(message = "Phone is required")
    private String phone;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;
    
    @NotBlank(message = "Password is required")
    private String password;
    
    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;
}