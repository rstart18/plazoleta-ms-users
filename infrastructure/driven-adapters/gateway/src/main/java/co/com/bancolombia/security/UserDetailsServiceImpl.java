package co.com.bancolombia.security;

import co.com.bancolombia.model.role.gateways.RoleRepository;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.model.userrole.UserRole;
import co.com.bancolombia.model.userrole.gateways.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());

        List<GrantedAuthority> authorities = userRoles.stream()
                .map(userRole -> roleRepository.findById(userRole.getRole().getId()))
                .filter(Objects::nonNull)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleKey()))
                .collect(Collectors.toList());


        return new UserDetailsImpl(user.getId(), user.getPhone(), user.getEmail(), user.getPassword(), authorities);
    }
}

