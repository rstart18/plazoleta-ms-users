package co.com.bancolombia.usecase.signin;

import co.com.bancolombia.model.authenticationresult.AuthenticationResult;
import co.com.bancolombia.model.credentials.Credentials;

public interface SignInService {
    AuthenticationResult execute(Credentials credentials);
}
