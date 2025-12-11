package co.com.bancolombia.api.mapper.dto.user;

import co.com.bancolombia.api.dto.request.CreateClientRequest;
import co.com.bancolombia.api.dto.response.CreateClientResponse;
import co.com.bancolombia.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientDtoMapper {
    User toUser(CreateClientRequest request);
    CreateClientResponse toResponse(User user);
}
