package csci812_project.backend.repository;

import csci812_project.backend.entity.Role;
import csci812_project.backend.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    // Find Role by name method
    Optional<Role> findByRoleName(RoleType roleName);
}
