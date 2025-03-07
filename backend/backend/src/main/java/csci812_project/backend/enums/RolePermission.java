package csci812_project.backend.enums;

public enum RolePermission {
    READ,
    WRITE,
    UPDATE,
    DELETE,
    MANAGE_USERS,  // Managers & Admins can manage users
    SYSTEM_ADMIN   // Full control over everything
}
