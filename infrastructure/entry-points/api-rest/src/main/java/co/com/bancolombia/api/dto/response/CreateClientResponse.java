package co.com.bancolombia.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String identityDocument;
    private String phone;
    private String email;
}
