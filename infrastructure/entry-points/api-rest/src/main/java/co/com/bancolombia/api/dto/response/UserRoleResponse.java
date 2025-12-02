package co.com.bancolombia.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserRoleResponse {
    private final List<String> roles;
}
