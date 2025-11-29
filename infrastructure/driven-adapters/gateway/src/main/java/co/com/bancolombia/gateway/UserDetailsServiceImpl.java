package co.com.bancolombia.gateway;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        System.out.println("=== DEBUG: Usuario encontrado: " + user.getEmail());
        System.out.println("=== DEBUG: Password hash: " + user.getPassword());

        // TEST: Verificar si el hash es correcto
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches("admin123", user.getPassword());
        System.out.println("=== DEBUG: Password matches: " + matches);

        List<GrantedAuthority> authority = new ArrayList<>();
        authority.add(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetails userDetails = new UserDetailsImpl(user.getEmail(), user.getPassword(), authority);
        System.out.println("=== DEBUG: UserDetails creado correctamente");

        return userDetails;
    }


//    public boolean isValidateRoles(String email, String rol) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
//        return user.getRole().equals(rol); // Ajustar según tu modelo
//    }
}

