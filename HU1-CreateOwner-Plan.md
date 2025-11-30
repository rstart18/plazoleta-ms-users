# Plan Técnico - HU1: Crear Propietario

## Historia de Usuario
**Rol**: Administrador  
**Historia**: Crear Propietario  
**Descripción**: Como Administrador necesito crear cuentas para propietarios para que puedan gestionar restaurantes  

## Criterios de Aceptación
1. ✅ Campos obligatorios: Nombre, Apellido, DocumentoDeIdentidad, celular, fechaNacimiento, correo, clave (BCrypt)
2. 🔄 Validaciones: email válido, teléfono máx 13 chars con +, documento numérico
3. 🔄 Asignar rol propietario automáticamente
4. 🔄 Usuario debe ser mayor de edad

---

## 1. Modelos de Dominio (domain/model)

### 1.1 Role Entity
```java
// domain/model/src/main/java/co/com/bancolombia/model/role/Role.java
public class Role {
    private Long id;
    private String name;
    private String key;
}
```

### 1.2 UserRole Entity
```java
// domain/model/src/main/java/co/com/bancolombia/model/userrole/UserRole.java
public class UserRole {
    private Long id;
    private Long userId;
    private Long roleId;
}
```

### 1.3 Role Enum
```java
// domain/model/src/main/java/co/com/bancolombia/model/role/RoleEnum.java
public enum RoleEnum {
    OWNER(1L, "OWNER"),
    ADMIN(2L, "ADMIN");
    
    private final Long id;
    private final String key;
}
```

### 1.4 DTOs
```java
// domain/model/src/main/java/co/com/bancolombia/model/createowner/CreateOwnerRequest.java
public class CreateOwnerRequest {
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotBlank @Pattern(regexp = "\\d+") private String documentId;
    @NotBlank @Pattern(regexp = "^\\+?[0-9]{1,13}$") private String phone;
    @NotNull private LocalDate birthDate;
    @NotBlank @Email private String email;
    @NotBlank private String password;
}

// domain/model/src/main/java/co/com/bancolombia/model/createowner/CreateOwnerResponse.java
public class CreateOwnerResponse {
    private Long id;
    private String email;
    private String fullName;
    private LocalDateTime createdAt;
}
```

---

## 2. Gateways (domain/model)

### 2.1 Repository Gateways
```java
// domain/model/src/main/java/co/com/bancolombia/model/role/gateways/RoleGateway.java
public interface RoleGateway {
    Optional<Role> findByKey(String key);
}

// domain/model/src/main/java/co/com/bancolombia/model/userrole/gateways/UserRoleGateway.java
public interface UserRoleGateway {
    UserRole save(UserRole userRole);
}
```

### 2.2 Service Gateway
```java
// domain/model/src/main/java/co/com/bancolombia/model/createowner/gateways/CreateOwnerService.java
public interface CreateOwnerService {
    CreateOwnerResponse createOwner(CreateOwnerRequest request);
}
```

---

## 3. Validador (domain/usecase)

### 3.1 OwnerValidator
```java
// domain/usecase/src/main/java/co/com/bancolombia/usecase/validator/OwnerValidator.java
@Component
public class OwnerValidator {
    
    public void validateAge(LocalDate birthDate) {
        // Validar mayor de edad (18 años)
    }
    
    public void validateEmail(String email) {
        // Validación adicional de email si es necesaria
    }
    
    public void validatePhone(String phone) {
        // Validar formato teléfono con + y máx 13 caracteres
    }
    
    public void validateDocumentId(String documentId) {
        // Validar que sea solo numérico
    }
}
```

---

## 4. Caso de Uso (domain/usecase)

### 4.1 CreateOwnerUseCase
```java
// domain/usecase/src/main/java/co/com/bancolombia/usecase/createowner/CreateOwnerUseCase.java
@Service
@RequiredArgsConstructor
public class CreateOwnerUseCase implements CreateOwnerService {
    
    private final UserGateway userGateway;
    private final RoleGateway roleGateway;
    private final UserRoleGateway userRoleGateway;
    private final OwnerValidator ownerValidator;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public CreateOwnerResponse createOwner(CreateOwnerRequest request) {
        // 1. Validar reglas de negocio
        ownerValidator.validateAge(request.getBirthDate());
        
        // 2. Verificar email no duplicado
        if (userGateway.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already exists");
        }
        
        // 3. Crear usuario
        User user = User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .documentId(request.getDocumentId())
            .phone(request.getPhone())
            .birthDate(request.getBirthDate())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .build();
            
        User savedUser = userGateway.save(user);
        
        // 4. Asignar rol OWNER
        Role ownerRole = roleGateway.findByKey(RoleEnum.OWNER.getKey())
            .orElseThrow(() -> new BusinessException("Owner role not found"));
            
        UserRole userRole = UserRole.builder()
            .userId(savedUser.getId())
            .roleId(ownerRole.getId())
            .build();
            
        userRoleGateway.save(userRole);
        
        // 5. Retornar response
        return CreateOwnerResponse.builder()
            .id(savedUser.getId())
            .email(savedUser.getEmail())
            .fullName(savedUser.getFirstName() + " " + savedUser.getLastName())
            .createdAt(savedUser.getCreatedAt())
            .build();
    }
}
```

---

## 5. Adaptadores JPA (infrastructure/driven-adapters/jpa-repository)

### 5.1 Entidades JPA
```java
// RoleEntity.java
@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id private Long id;
    @Column(name = "name") private String name;
    @Column(name = "key") private String key;
}

// UserRoleEntity.java
@Entity
@Table(name = "user_roles")
public class UserRoleEntity {
    @Id @GeneratedValue private Long id;
    @Column(name = "user_id") private Long userId;
    @Column(name = "role_id") private Long roleId;
}
```

### 5.2 Repositorios JPA
```java
// RoleJPARepository.java
public interface RoleJPARepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByKey(String key);
}

// UserRoleJPARepository.java
public interface UserRoleJPARepository extends JpaRepository<UserRoleEntity, Long> {
}
```

### 5.3 Adaptadores
```java
// RoleRepositoryAdapter.java
@Repository
public class RoleRepositoryAdapter implements RoleGateway {
    // Implementación con ModelMapper
}

// UserRoleRepositoryAdapter.java
@Repository  
public class UserRoleRepositoryAdapter implements UserRoleGateway {
    // Implementación con ModelMapper
}
```

---

## 6. Controlador REST (infrastructure/entry-points/api-rest)

### 6.1 OwnerController
```java
// infrastructure/entry-points/api-rest/src/main/java/co/com/bancolombia/api/owner/OwnerController.java
@RestController
@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
public class OwnerController {
    
    private final CreateOwnerService createOwnerService;
    
    @PostMapping
    public ResponseEntity<CreateOwnerResponse> createOwner(@Valid @RequestBody CreateOwnerRequest request) {
        CreateOwnerResponse response = createOwnerService.createOwner(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
```

---

## 7. Scripts SQL

### 7.1 Tabla Roles
```sql
-- infrastructure/driven-adapters/jpa-repository/src/main/resources/db/migration/V2__create_roles_table.sql
CREATE TABLE roles (
    id BIGINT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    key VARCHAR(20) NOT NULL UNIQUE
);
```

### 7.2 Datos Maestros Roles
```sql
-- infrastructure/driven-adapters/jpa-repository/src/main/resources/db/migration/V3__insert_roles.sql
INSERT INTO roles (id, name, key) VALUES 
(1, 'Owner', 'OWNER'),
(2, 'Admin', 'ADMIN');
```

### 7.3 Tabla UserRole
```sql
-- infrastructure/driven-adapters/jpa-repository/src/main/resources/db/migration/V4__create_user_roles_table.sql
CREATE TABLE user_roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    UNIQUE KEY unique_user_role (user_id, role_id)
);
```

---

## 8. Tests Unitarios

### 8.1 OwnerValidatorTest
```java
class OwnerValidatorTest {
    @Test void testValidateAge_Valid() { }
    @Test void testValidateAge_Invalid() { }
    @Test void testValidateEmail_Valid() { }
    @Test void testValidateEmail_Invalid() { }
    @Test void testValidatePhone_Valid() { }
    @Test void testValidatePhone_Invalid() { }
    @Test void testValidateDocumentId_Valid() { }
    @Test void testValidateDocumentId_Invalid() { }
}
```

### 8.2 CreateOwnerUseCaseTest
```java
class CreateOwnerUseCaseTest {
    @Test void testCreateOwner_Success() { }
    @Test void testCreateOwner_InvalidAge() { }
    @Test void testCreateOwner_DuplicateEmail() { }
    @Test void testCreateOwner_RoleNotFound() { }
}
```

### 8.3 OwnerControllerTest
```java
class OwnerControllerTest {
    @Test void testCreateOwner_Success() { }
    @Test void testCreateOwner_ValidationError() { }
    @Test void testCreateOwner_BusinessException() { }
}
```

---

## 9. Excepciones de Dominio

### 9.1 Custom Exceptions
```java
// domain/model/src/main/java/co/com/bancolombia/model/exception/DuplicateEmailException.java
public class DuplicateEmailException extends BusinessException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}

// domain/model/src/main/java/co/com/bancolombia/model/exception/InvalidAgeException.java
public class InvalidAgeException extends BusinessException {
    public InvalidAgeException(String message) {
        super(message);
    }
}
```

---

## 10. Flujo de Ejecución

1. **POST /api/v1/owners** → OwnerController recibe CreateOwnerRequest
2. **@Valid** valida constraints del request (email, notblank, pattern)
3. **CreateOwnerUseCase** ejecuta validaciones de negocio via OwnerValidator
4. **CreateOwnerUseCase** verifica email no duplicado
5. **CreateOwnerUseCase** encripta password con BCrypt
6. **CreateOwnerUseCase** guarda usuario via UserGateway
7. **CreateOwnerUseCase** asigna rol OWNER via UserRoleGateway
8. **OwnerController** retorna CreateOwnerResponse con status 201

---

## 11. Checklist de Implementación

### Dominio
- [ ] Role entity y enum
- [ ] UserRole entity  
- [ ] CreateOwnerRequest/Response DTOs
- [ ] RoleGateway y UserRoleGateway interfaces
- [ ] CreateOwnerService interface
- [ ] Custom exceptions

### Caso de Uso
- [ ] OwnerValidator con validaciones de negocio
- [ ] CreateOwnerUseCase implementando CreateOwnerService

### Infraestructura
- [ ] RoleEntity y UserRoleEntity JPA
- [ ] RoleJPARepository y UserRoleJPARepository
- [ ] RoleRepositoryAdapter y UserRoleRepositoryAdapter
- [ ] Scripts SQL para roles y user_roles
- [ ] OwnerController REST

### Testing
- [ ] OwnerValidatorTest
- [ ] CreateOwnerUseCaseTest  
- [ ] OwnerControllerTest

### Configuración
- [ ] Bean registration para nuevos componentes
- [ ] ModelMapper configuration para nuevas entidades