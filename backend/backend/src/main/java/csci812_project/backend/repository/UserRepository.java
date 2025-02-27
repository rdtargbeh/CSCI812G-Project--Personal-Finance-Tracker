package csci812_project.backend.repository;

import csci812_project.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /** Find user by username */
    Optional<User> findByUserName(String userName);

    Optional<User> findByUserIdAndIsDeletedFalse(Long userId); // ✅ Exclude deleted users

    Page<User> findByIsDeletedFalse(Pageable pageable); // ✅ Fetch only non-deleted users

    /** Check if a username already exists */
    boolean existsByUserName(String userName);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByUserNameOrEmail(String username, String email);

}

