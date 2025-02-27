package csci812_project.backend.service;

import csci812_project.backend.dto.UserDTO;
import csci812_project.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {


    /**
     * Retrieves user details by user ID.
     * @param userId ID of the user.
     * @return UserDTO containing user information.
     */
    UserDTO getUserById(Long userId);


    /**
     * Retrieves a paginated list of users.
     * @param pageable Pagination and sorting parameters.
     * @return Page<UserDTO> containing paginated user data.
     */
    Page<UserDTO> getAllUsers(Pageable pageable);

    /**
     * Updates user profile information.
     * @param userId ID of the user.
     * @param userDTO Updated user data.
     * @return Updated UserDTO.
     */
    UserDTO updateUser(Long userId, UserDTO userDTO);

    /**
     * Marks a user account as deleted (soft delete).
     * @param userId ID of the user to delete.
     */
    void deleteUser(Long userId);

    /**
     * Restores a soft-deleted user account.
     * @param userId ID of the user to restore.
     */
    void restoreDeletedUser(Long userId);


    /**
     * Creates a new user account with validation checks.
     * @param userDTO User registration data.
     * @return Created UserDTO with user details.
     */
    UserDTO register(UserDTO userDTO);

    boolean authenticate(String username, String password);


//    UserDTO authenticate(String username, String password);

//    String login(UserDTO userDTO);

}