package csci812_project.backend.entity;

import csci812_project.backend.enums.RolePermission;
import csci812_project.backend.enums.RoleType;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", unique = true, nullable = false, length = 30)
    private RoleType roleName;  // Example: "ADMIN", "USER", "MANAGER"

    @Column(name = "description")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)  // ✅ Permissions stored separately
    @Enumerated(EnumType.STRING)
    private Set<RolePermission> permissions = new HashSet<>();

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)  // Many roles per user
    private Set<User> users = new HashSet<>();


    @Override
    public String toString(){
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


    // Constructor
    public Role() {}
    public Role(RoleType roleName, String description, Set<RolePermission> permissions) {
        this.description = description;
        this.roleName = roleName;
        this.permissions = permissions;
    }

    // Getters, Setters, Constructors

    public RoleType getRoleName() {return roleName;}

    public void setRoleName(RoleType roleName) {this.roleName = roleName;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public Set<RolePermission> getPermissions() {return permissions;}

    public void setPermissions(Set<RolePermission> permissions) {this.permissions = permissions;}
}
