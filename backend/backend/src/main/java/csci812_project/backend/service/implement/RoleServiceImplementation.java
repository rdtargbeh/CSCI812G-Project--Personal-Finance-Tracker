package csci812_project.backend.service.implement;

import csci812_project.backend.entity.Role;
import csci812_project.backend.enums.RolePermission;
import csci812_project.backend.enums.RoleType;
import csci812_project.backend.repository.RoleRepository;
import csci812_project.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class RoleServiceImplementation implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role createRole(RoleType roleName, String description, Set<RolePermission> permissions) {
        Role role = new Role(roleName, description, permissions);
        return roleRepository.save(role);
    }

    @Override
    public Role findByRoleName(RoleType roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
    }
}
