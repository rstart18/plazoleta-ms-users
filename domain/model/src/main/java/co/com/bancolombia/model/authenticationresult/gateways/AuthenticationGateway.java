package co.com.bancolombia.model.authenticationresult.gateways;

import co.com.bancolombia.model.authenticationresult.AuthenticationResult;

public interface AuthenticationGateway {
    AuthenticationResult authenticate(String username, String password);
}