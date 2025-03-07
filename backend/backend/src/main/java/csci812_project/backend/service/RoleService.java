package csci812_project.backend.service;

import csci812_project.backend.entity.Role;
import csci812_project.backend.enums.RolePermission;
import csci812_project.backend.enums.RoleType;

import java.util.Set;

public interface RoleService {

    Role findByRoleName(RoleType roleName);

    Role createRole(RoleType roleName, String description, Set<RolePermission> permissions);
}
