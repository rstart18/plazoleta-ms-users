package co.com.bancolombia.api.mapper.dto.employee;

import co.com.bancolombia.api.dto.request.CreateEmployeeRequest;
import co.com.bancolombia.api.dto.response.CreateEmployeeResponse;
import co.com.bancolombia.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeDtoMapper {

    User toUser(CreateEmployeeRequest request);

    CreateEmployeeResponse toResponse(User user);
}
