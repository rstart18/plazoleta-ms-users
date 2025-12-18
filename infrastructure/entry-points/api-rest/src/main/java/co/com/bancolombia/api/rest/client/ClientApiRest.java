package co.com.bancolombia.api.rest.client;

import co.com.bancolombia.api.dto.request.CreateClientRequest;
import co.com.bancolombia.api.dto.response.ApiResponseData;
import co.com.bancolombia.api.dto.response.CreateClientResponse;
import co.com.bancolombia.api.mapper.dto.user.ClientDtoMapper;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.usecase.client.ClientService;
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
@RequestMapping(value = "/api/clients", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ClientApiRest {

    private final ClientService clientService;
    private final ClientDtoMapper clientDtoMapper;

    @PostMapping
    public ResponseEntity<ApiResponseData<CreateClientResponse>> createClient(@Valid @RequestBody CreateClientRequest request) {
        User client = clientDtoMapper.toUser(request);
        User clientCreated = clientService.createClient(client);
        CreateClientResponse response = clientDtoMapper.toResponse(clientCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseData.of(response));
    }
}
