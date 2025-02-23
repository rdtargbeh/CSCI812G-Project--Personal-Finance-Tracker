package csci812_project.backend.repository;

import csci812_project.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /** Find user by username */
    Optional<User> findByUserName(String userName);

    /** Check if a username already exists */
    boolean existsByUserName(String userName);


}

