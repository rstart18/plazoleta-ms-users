package co.com.bancolombia.usecase.client;

import co.com.bancolombia.model.enums.DomainErrorCode;
import co.com.bancolombia.model.enums.RoleEnum;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.role.Role;
import co.com.bancolombia.model.role.gateways.RoleRepository;
import co.com.bancolombia.model.security.gateways.PasswordEncoderGateway;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.model.userrole.UserRole;
import co.com.bancolombia.model.userrole.gateways.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateClientUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoderGateway passwordEncoder;

    @InjectMocks
    private ClientUseCase clientUseCase;

    private User client;
    private Role clientRole;

    @BeforeEach
    void setUp() {
        client = User.builder()
                .email("test@example.com")
                .identityDocument("12345678")
                .password("password123")
                .build();

        clientRole = Role.builder()
                .roleKey(RoleEnum.CLIENT.getRoleKey())
                .build();
    }

    @Test
    void shouldCreateClientSuccessfully() {
        // Given
        when(userRepository.findByEmail(client.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByIdentityDocument(client.getIdentityDocument())).thenReturn(Optional.empty());
        when(roleRepository.findByRoleKey(RoleEnum.CLIENT.getRoleKey())).thenReturn(Optional.of(clientRole));
        when(passwordEncoder.encode(client.getPassword())).thenReturn("encodedPassword");
        when(userRepository.create(any(User.class))).thenReturn(client);

        // When
        User result = clientUseCase.createClient(client);

        // Then
        assertNotNull(result);
        verify(userRepository).create(any(User.class));
        verify(userRoleRepository).create(any(UserRole.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Given
        when(userRepository.findByEmail(client.getEmail())).thenReturn(Optional.of(client));

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class, 
                () -> clientUseCase.createClient(client));
        assertEquals(DomainErrorCode.USER_ALREADY_EXISTS.getCode(), exception.getCode());
    }

    @Test
    void shouldThrowExceptionWhenIdentityDocumentAlreadyExists() {
        // Given
        when(userRepository.findByEmail(client.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByIdentityDocument(client.getIdentityDocument())).thenReturn(Optional.of(client));

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class, 
                () -> clientUseCase.createClient(client));
        assertEquals(DomainErrorCode.USER_ALREADY_EXISTS.getCode(), exception.getCode());
    }

    @Test
    void shouldThrowExceptionWhenRoleNotFound() {
        // Given
        when(userRepository.findByEmail(client.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByIdentityDocument(client.getIdentityDocument())).thenReturn(Optional.empty());
        when(roleRepository.findByRoleKey(RoleEnum.CLIENT.getRoleKey())).thenReturn(Optional.empty());

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class, 
                () -> clientUseCase.createClient(client));
        assertEquals(DomainErrorCode.ROLE_NOT_FOUND.getCode(), exception.getCode());
    }
}