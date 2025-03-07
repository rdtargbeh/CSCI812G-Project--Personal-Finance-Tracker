package csci812_project.backend.enums;

public enum RoleType {
    ADMIN,   // 🔹 Full system access (Manage Users, CRUD)
    MANAGER, // 🔹 Manage users, CRUD (but limited system control)
    USER     // 🔹 Can create/update data but cannot delete users
}
