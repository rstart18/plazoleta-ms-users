package co.com.bancolombia.api.mapper.dto.auth;

import co.com.bancolombia.api.dto.request.AuthCredentialsRequest;
import co.com.bancolombia.api.dto.response.AuthenticationResponse;
import co.com.bancolombia.model.authenticationresult.AuthenticationResult;
import co.com.bancolombia.model.credentials.Credentials;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AuthDtoMapper {

    @Mapping(source = "email", target = "username")
    Credentials toCredentials(AuthCredentialsRequest request);

    @Mapping(source = "authenticatedUser", target = "user")
    AuthenticationResponse toResponse(AuthenticationResult result);
}
