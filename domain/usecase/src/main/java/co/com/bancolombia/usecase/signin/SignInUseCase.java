package co.com.bancolombia.usecase.signin;

import co.com.bancolombia.model.authenticationresult.gateways.AuthenticationGateway;
import co.com.bancolombia.model.authenticationresult.AuthenticationResult;
import co.com.bancolombia.model.credentials.Credentials;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class SignInUseCase implements SignInService {
    private final AuthenticationGateway authenticationGateway;

    @Override
    public AuthenticationResult execute(Credentials credentials) {
        return authenticationGateway.authenticate(
                credentials.username(),
                credentials.password()
        );
    }
}
