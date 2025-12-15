package co.com.bancolombia.api.mapper.dto.user;

import co.com.bancolombia.api.dto.request.CreateEmployeeRequest;
import co.com.bancolombia.api.dto.response.CreateEmployeeResponse;
import co.com.bancolombia.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeDtoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toUser(CreateEmployeeRequest request);

    @Mapping(target = "restaurantId", source = "restaurantId")
    CreateEmployeeResponse toResponse(User user, Long restaurantId);
}