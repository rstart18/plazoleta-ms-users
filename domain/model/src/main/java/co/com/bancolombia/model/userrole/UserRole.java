package co.com.bancolombia.model.userrole;
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
public class UserRole {
    private Long id;
    private Long userId;
    private Long roleId;
}
