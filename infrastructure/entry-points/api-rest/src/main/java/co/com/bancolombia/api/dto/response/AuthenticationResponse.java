package co.com.bancolombia.api.dto.response;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class AuthenticationResponse {
    private String token;
    private UserResponse user;
}
