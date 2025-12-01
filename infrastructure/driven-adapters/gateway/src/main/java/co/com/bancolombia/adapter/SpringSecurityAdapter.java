package co.com.bancolombia.adapter;

import co.com.bancolombia.model.authenticationresult.AuthenticationResult;
import co.com.bancolombia.model.authenticationresult.gateways.AuthenticationGateway;
import co.com.bancolombia.model.exception.InvalidCredentialsException;
import co.com.bancolombia.model.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SpringSecurityAdapter implements AuthenticationGateway {
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.secret}")
    private String jwtSecret;

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
        SecretKeySpec key = new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA256");

        String roles = auth.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(auth.getName())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    private User mapToUser(Authentication auth) {
        return User.builder()
                .email(auth.getName())
                .build();
    }
}
