package co.com.bancolombia.usecase.signin;

import co.com.bancolombia.model.authenticationresult.AuthenticationResult;
import co.com.bancolombia.model.authenticationresult.gateways.AuthenticationGateway;
import co.com.bancolombia.model.credentials.Credentials;
import co.com.bancolombia.model.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignInUseCaseTest {

    @Mock
    private AuthenticationGateway authenticationGateway;

    @InjectMocks
    private SignInUseCase signInUseCase;

    @Test
    void shouldSignInSuccessfully() {
        // Given
        Credentials credentials = new Credentials("user@example.com", "password");
        AuthenticationResult expected = AuthenticationResult.builder()
                .token("jwt-token")
                .authenticatedUser(User.builder().email("user@example.com").build())
                .build();

        when(authenticationGateway.authenticate(credentials.username(), credentials.password()))
                .thenReturn(expected);

        // When
        AuthenticationResult result = signInUseCase.execute(credentials);

        // Then
        assertEquals(expected, result);
        verify(authenticationGateway).authenticate(credentials.username(), credentials.password());
    }
}
