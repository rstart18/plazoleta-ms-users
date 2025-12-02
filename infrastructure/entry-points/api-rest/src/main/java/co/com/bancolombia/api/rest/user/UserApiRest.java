package co.com.bancolombia.api.rest.user;

import co.com.bancolombia.api.constans.SecurityConstants;
import co.com.bancolombia.api.dto.response.ApiResponse;
import co.com.bancolombia.api.dto.response.UserRoleResponse;
import co.com.bancolombia.usecase.getuserrole.GetUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserApiRest {

    private final GetUserRoleService getUserRoleService;

    @GetMapping("/{userId}/roles")
    @PreAuthorize(SecurityConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<UserRoleResponse>> getUserRoles(@PathVariable("userId") Long userId) {
        List<String> roles = getUserRoleService.getUserRoles(userId);
        UserRoleResponse response = new UserRoleResponse(roles);
        return ResponseEntity.ok(ApiResponse.of(response));
    }
}
