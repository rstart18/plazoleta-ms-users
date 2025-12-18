package co.com.bancolombia.api.rest.owner;

import co.com.bancolombia.api.config.JwtUserInterceptor;
import co.com.bancolombia.api.dto.request.CreateOwnerRequest;
import co.com.bancolombia.api.dto.response.ApiResponseData;
import co.com.bancolombia.api.dto.response.CreateOwnerResponse;
import co.com.bancolombia.api.mapper.dto.user.OwnerDtoMapper;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.usecase.owner.OwnerService;
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
@RequestMapping(value = "/api/owners", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OwnerApiRest {

    private final OwnerService createOwnerService;
    private final OwnerDtoMapper ownerDtoMapper;

    @PostMapping
    public ResponseEntity<ApiResponseData<CreateOwnerResponse>> createOwner(
            @Valid @RequestBody CreateOwnerRequest request,
            HttpServletRequest httpRequest) {
        String userRole = JwtUserInterceptor.getUserRole(httpRequest);
        User owner = ownerDtoMapper.toUser(request);
        User ownerCreated = createOwnerService.createOwner(owner, userRole);
        CreateOwnerResponse response = ownerDtoMapper.toResponse(ownerCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseData.of(response));
    }
}
