package co.com.bancolombia.api.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthCredentialsRequest {
    private String email;
    private String password;
}