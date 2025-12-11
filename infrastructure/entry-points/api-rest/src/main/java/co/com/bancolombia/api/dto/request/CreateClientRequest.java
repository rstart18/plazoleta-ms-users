package co.com.bancolombia.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientRequest {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Identity document is required")
    @Pattern(regexp = "\\d+", message = "Identity document must be numeric only")
    private String identityDocument;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^(\\+[0-9]{1,12}|[0-9]{1,13})$", message = "Phone must contain maximum 13 characters and can include + symbol")
    private String phone;

    @NotBlank(message = "Birth date is required")
    private String birthDate;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
