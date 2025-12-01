package co.com.bancolombia.api.rest.owner;

import co.com.bancolombia.api.constans.SecurityConstants;
import co.com.bancolombia.api.dto.request.CreateOwnerRequest;
import co.com.bancolombia.api.dto.response.ApiResponse;
import co.com.bancolombia.api.dto.response.CreateOwnerResponse;
import co.com.bancolombia.api.mapper.dto.user.OwnerDtoMapper;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.usecase.createowner.CreateOwnerService;
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
@RequestMapping(value = "/api/owners", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OwnerApiRest {

    private final CreateOwnerService createOwnerService;
    private final OwnerDtoMapper ownerDtoMapper;

    @PostMapping
    @PreAuthorize(SecurityConstants.ROLE_ADMIN)
    public ResponseEntity<ApiResponse<CreateOwnerResponse>> createOwner(@Valid @RequestBody CreateOwnerRequest request) {
        User owner = ownerDtoMapper.toUser(request);
        User ownerCreated = createOwnerService.execute(owner);
        CreateOwnerResponse response = ownerDtoMapper.toResponse(ownerCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(response));
    }
}
