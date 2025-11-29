package co.com.bancolombia.api.dto.response;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class UserResponse {
    private String name;
    private String email;
}
