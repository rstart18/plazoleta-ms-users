package co.com.bancolombia.api.rest;

import co.com.bancolombia.api.config.JwtUserInterceptor;
import co.com.bancolombia.api.constans.SecurityConstants;
import co.com.bancolombia.api.dto.request.CreateEmployeeRequest;
import co.com.bancolombia.api.dto.response.ApiResponse;
import co.com.bancolombia.api.dto.response.CreateEmployeeResponse;
import co.com.bancolombia.api.mapper.dto.employee.EmployeeDtoMapper;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.usecase.createemploye.CreateEmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/employees", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EmployeeApiRest {

    private final CreateEmployeeService createEmployeeService;
    private final EmployeeDtoMapper employeeDtoMapper;

    @PostMapping
    @PreAuthorize(SecurityConstants.ROLE_OWNER)
    public ResponseEntity<ApiResponse<CreateEmployeeResponse>> createEmployee(
            @Valid @RequestBody CreateEmployeeRequest request,
            HttpServletRequest httpRequest) {

        Long ownerId = JwtUserInterceptor.getUserId(httpRequest);
        String authToken = httpRequest.getHeader("Authorization");
        User employee = employeeDtoMapper.toUser(request);
        User employeeCreated = createEmployeeService.createEmployee(
                employee,
                request.getRestaurantId(),
                ownerId,
                authToken
        );
        CreateEmployeeResponse response = employeeDtoMapper.toResponse(employeeCreated);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of(response));
    }
}
