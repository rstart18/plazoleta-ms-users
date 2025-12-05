package co.com.bancolombia.usecase.createemploye;

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
import co.com.bancolombia.usecase.createowner.CreateOwnerUseCase;
import co.com.bancolombia.validator.OwnerValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateOwnerUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private OwnerValidator ownerValidator;

    @Mock
    private PasswordEncoderGateway passwordEncoder;

    @InjectMocks
    private CreateOwnerUseCase createOwnerUseCase;

    private User owner;
    private Role ownerRole;
    private User savedUser;

    @BeforeEach
    void setUp() {
        owner = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .identityDocument("12345678")
                .phone("+573001234567")
                .birthDate(LocalDate.of(1990, 1, 1))
                .password("password123")
                .build();

        ownerRole = Role.builder()
                .id(2L)
                .name("OWNER")
                .roleKey("OWNER")
                .build();

        savedUser = owner.toBuilder()
                .id(1L)
                .build();
    }

    @Test
    void shouldCreateOwnerSuccessfully() {
        // Given
        when(userRepository.findByEmail(owner.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByIdentityDocument(owner.getIdentityDocument())).thenReturn(Optional.empty());
        when(roleRepository.findByRoleKey(RoleEnum.OWNER.getRoleKey())).thenReturn(Optional.of(ownerRole));
        when(passwordEncoder.encode(owner.getPassword())).thenReturn("encodedPassword");
        when(userRepository.create(any(User.class))).thenReturn(savedUser);
        when(userRoleRepository.create(any(UserRole.class))).thenReturn(any(UserRole.class));

        // When
        User result = createOwnerUseCase.createOwner(owner);

        // Then
        assertNotNull(result);
        assertEquals(savedUser.getId(), result.getId());
        assertEquals(savedUser.getEmail(), result.getEmail());

        verify(ownerValidator).validateCreateOwner(owner);
        verify(userRepository).findByEmail(owner.getEmail());
        verify(userRepository).findByIdentityDocument(owner.getIdentityDocument());
        verify(roleRepository).findByRoleKey(RoleEnum.OWNER.getRoleKey());
        verify(passwordEncoder).encode(owner.getPassword());
        verify(userRepository).create(any(User.class));
        verify(userRoleRepository).create(any(UserRole.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Given
        when(userRepository.findByEmail(owner.getEmail())).thenReturn(Optional.of(owner));

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> createOwnerUseCase.createOwner(owner));

        assertEquals(DomainErrorCode.USER_ALREADY_EXISTS.getCode(), exception.getCode());
        assertEquals(DomainErrorCode.USER_ALREADY_EXISTS.getMessage(), exception.getMessage());

        verify(ownerValidator).validateCreateOwner(owner);
        verify(userRepository).findByEmail(owner.getEmail());
        verify(userRepository, never()).create(any());
    }

    @Test
    void shouldThrowExceptionWhenIdentityDocumentAlreadyExists() {
        // Given
        when(userRepository.findByEmail(owner.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByIdentityDocument(owner.getIdentityDocument())).thenReturn(Optional.of(owner));

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> createOwnerUseCase.createOwner(owner));

        assertEquals(DomainErrorCode.USER_ALREADY_EXISTS.getCode(), exception.getCode());
        assertEquals(DomainErrorCode.USER_ALREADY_EXISTS.getMessage(), exception.getMessage());

        verify(ownerValidator).validateCreateOwner(owner);
        verify(userRepository).findByEmail(owner.getEmail());
        verify(userRepository).findByIdentityDocument(owner.getIdentityDocument());
        verify(userRepository, never()).create(any());
    }

    @Test
    void shouldThrowExceptionWhenOwnerRoleNotFound() {
        // Given
        when(userRepository.findByEmail(owner.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByIdentityDocument(owner.getIdentityDocument())).thenReturn(Optional.empty());
        when(roleRepository.findByRoleKey(RoleEnum.OWNER.getRoleKey())).thenReturn(Optional.empty());

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> createOwnerUseCase.createOwner(owner));

        assertEquals(DomainErrorCode.ROLE_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(DomainErrorCode.ROLE_NOT_FOUND.getMessage(), exception.getMessage());

        verify(ownerValidator).validateCreateOwner(owner);
        verify(userRepository).findByEmail(owner.getEmail());
        verify(userRepository).findByIdentityDocument(owner.getIdentityDocument());
        verify(roleRepository).findByRoleKey(RoleEnum.OWNER.getRoleKey());
        verify(userRepository, never()).create(any());
    }

    @Test
    void shouldThrowExceptionWhenValidationFails() {
        // Given
        doThrow(new BusinessException(DomainErrorCode.INVALID_USER_DATA.getCode(), "Invalid age"))
                .when(ownerValidator).validateCreateOwner(owner);

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class,
                () -> createOwnerUseCase.createOwner(owner));

        assertEquals(DomainErrorCode.INVALID_USER_DATA.getCode(), exception.getCode());
        assertEquals("Invalid age", exception.getMessage());

        verify(ownerValidator).validateCreateOwner(owner);
        verify(userRepository, never()).findByEmail(any());
        verify(userRepository, never()).create(any());
    }
}
