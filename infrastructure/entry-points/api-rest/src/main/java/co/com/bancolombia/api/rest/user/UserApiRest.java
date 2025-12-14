package co.com.bancolombia.api.rest.user;

import co.com.bancolombia.api.config.JwtUserInterceptor;
import co.com.bancolombia.api.dto.response.ApiResponse;
import co.com.bancolombia.api.dto.response.UserRoleResponse;
import co.com.bancolombia.usecase.userrole.UserRoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserApiRest {

    private final UserRoleService getUserRoleService;

    @GetMapping("/{userId}/roles")
    public ResponseEntity<ApiResponse<UserRoleResponse>> getUserRoles(
            @PathVariable("userId") Long userId,
            HttpServletRequest httpRequest) {
        String userRole = JwtUserInterceptor.getUserRole(httpRequest);
        List<String> roles = getUserRoleService.getUserRoles(userId, userRole);
        UserRoleResponse response = new UserRoleResponse(roles);
        return ResponseEntity.ok(ApiResponse.of(response));
    }
}
