package co.com.bancolombia.api.rest.auth;
import co.com.bancolombia.api.dto.request.AuthCredentialsRequest;
import co.com.bancolombia.api.dto.response.AuthenticationResponse;
import co.com.bancolombia.api.mapper.dto.auth.AuthDtoMapper;
import co.com.bancolombia.model.authenticationresult.AuthenticationResult;
import co.com.bancolombia.model.credentials.Credentials;
import co.com.bancolombia.usecase.signin.SignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthApiRest {

    private final SignInService signInUseCase;
    private final AuthDtoMapper authDtoMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody AuthCredentialsRequest request) {
        System.out.println("=== DEBUG: Request recibido - email: " + request.getEmail() + ", password: " + (request.getPassword() != null ? "***" : "null"));

        Credentials credentials = authDtoMapper.toCredentials(request);
        System.out.println("=== DEBUG: Credentials mapeado - username: " + credentials.username() + ", password: " + (credentials.password() != null ? "***" : "null"));

        AuthenticationResult result = signInUseCase.execute(credentials);
        AuthenticationResponse response = authDtoMapper.toResponse(result);
        return ResponseEntity.ok(response);
    }
}
