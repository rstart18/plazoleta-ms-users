package co.com.bancolombia.model.security.gateways;

public interface PasswordEncoderGateway {
    String encode(String rawPassword);
}