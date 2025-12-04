package co.com.bancolombia.model.enums;

public enum RoleEnum {
    ADMIN(1L, "ADMIN"),
    OWNER(2L, "OWNER"),
    EMPLOYEE(3L, "EMPLOYEE"),    // Nuevo
    CLIENT(4L, "CLIENT");

    private final Long id;
    private final String roleKey;

    RoleEnum(Long id, String roleKey) {
        this.id = id;
        this.roleKey = roleKey;
    }

    public Long getId() {
        return id;
    }

    public String getRoleKey() {
        return roleKey;
    }
}
