package co.com.bancolombia.api.mapper.dto.user;

import co.com.bancolombia.api.dto.response.UserResponse;
import co.com.bancolombia.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserResponse toResponse(User user);
}
