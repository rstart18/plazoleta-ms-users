package co.com.bancolombia.api.mapper.dto.user;

import co.com.bancolombia.api.dto.request.CreateOwnerRequest;
import co.com.bancolombia.api.dto.response.CreateOwnerResponse;
import co.com.bancolombia.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OwnerDtoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toUser(CreateOwnerRequest request);

    CreateOwnerResponse toResponse(User user);
}