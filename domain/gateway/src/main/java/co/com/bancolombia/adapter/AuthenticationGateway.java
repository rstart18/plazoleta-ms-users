package co.com.bancolombia.adapter;

import co.com.bancolombia.model.authenticationresult.AuthenticationResult;

public interface AuthenticationGateway {
    AuthenticationResult authenticate(String username, String password);
}
