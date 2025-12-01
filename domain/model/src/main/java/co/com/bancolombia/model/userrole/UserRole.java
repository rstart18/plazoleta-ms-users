package co.com.bancolombia.model.userrole;

import co.com.bancolombia.model.role.Role;
import co.com.bancolombia.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserRole {
    private Long id;
    private User user;
    private Role role;
}
