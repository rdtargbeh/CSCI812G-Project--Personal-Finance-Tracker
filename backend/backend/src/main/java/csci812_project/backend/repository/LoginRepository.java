package csci812_project.backend.repository;

import csci812_project.backend.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {

    Optional<Login> findByUserName(String userName);

    Optional<Login> findByEmail(String email);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    /** ✅ Find login details by user ID */
    Optional<Login> findByUserId(Long userId);
}
