package co.com.bancolombia.model.authenticationresult;
import co.com.bancolombia.model.user.User;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AuthenticationResult {
    private final boolean successful;
    private final String token;
    private final User authenticatedUser;
}
