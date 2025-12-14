package co.com.bancolombia.api.rest.employee;

import co.com.bancolombia.api.config.JwtUserInterceptor;
import co.com.bancolombia.api.dto.request.CreateEmployeeRequest;
import co.com.bancolombia.api.dto.response.ApiResponse;
import co.com.bancolombia.api.dto.response.CreateEmployeeResponse;
import co.com.bancolombia.api.mapper.dto.user.EmployeeDtoMapper;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.usecase.employee.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/employees", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EmployeeApiRest {

    private final EmployeeService employeeService;
    private final EmployeeDtoMapper employeeDtoMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateEmployeeResponse>> createEmployee(
            @Valid @RequestBody CreateEmployeeRequest request,
            HttpServletRequest httpRequest) {
        String userRole = JwtUserInterceptor.getUserRole(httpRequest);
        Long ownerId = JwtUserInterceptor.getUserId(httpRequest);
        
        User employee = employeeDtoMapper.toUser(request);
        User employeeCreated = employeeService.createEmployee(employee, request.getRestaurantId(), userRole, ownerId);
        CreateEmployeeResponse response = employeeDtoMapper.toResponse(employeeCreated, request.getRestaurantId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(response));
    }
}