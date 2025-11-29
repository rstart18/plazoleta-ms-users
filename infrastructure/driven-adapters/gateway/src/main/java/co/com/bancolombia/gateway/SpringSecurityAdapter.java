package co.com.bancolombia.gateway;

import co.com.bancolombia.model.authenticationresult.AuthenticationResult;
import co.com.bancolombia.model.authenticationresult.gateways.AuthenticationGateway;
import co.com.bancolombia.model.exception.InvalidCredentialsException;
import co.com.bancolombia.model.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class SpringSecurityAdapter implements AuthenticationGateway {
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResult authenticate(String username, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            return AuthenticationResult.builder()
                    .token(generateToken(auth))
                    .authenticatedUser(mapToUser(auth))
                    .build();

        } catch (AuthenticationException ex) {
            throw new InvalidCredentialsException();
        }
    }

    private String generateToken(Authentication auth) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Jwts.builder()
                .setSubject(auth.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 horas
                .signWith(key)
                .compact();
    }

    private User mapToUser(Authentication auth) {
        return User.builder()
                .email(auth.getName())
                .build();
    }
}
