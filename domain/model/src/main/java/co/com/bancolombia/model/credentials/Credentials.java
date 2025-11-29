package co.com.bancolombia.model.credentials;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

//@NoArgsConstructor
@Builder(toBuilder = true)
public record Credentials(String username, String password) {
}
