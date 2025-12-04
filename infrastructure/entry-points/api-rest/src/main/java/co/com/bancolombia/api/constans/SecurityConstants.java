package co.com.bancolombia.api.constans;

public final class SecurityConstants {

    public static final String ROLE_ADMIN = "hasRole('ADMIN')";
    public static final String ROLE_OWNER = "hasRole('OWNER')";
    public static final String ROLE_EMPLOYEE = "hasRole('EMPLOYEE')";
    public static final String ROLE_CLIENT = "hasRole('CLIENT')";

    private SecurityConstants() {
    }
}