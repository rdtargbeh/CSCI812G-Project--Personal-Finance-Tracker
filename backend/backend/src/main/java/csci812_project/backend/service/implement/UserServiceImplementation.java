package csci812_project.backend.service.implement;

import csci812_project.backend.dto.UserDTO;
import csci812_project.backend.entity.Login;
import csci812_project.backend.entity.User;
import csci812_project.backend.mapper.UserMapper;
import csci812_project.backend.repository.LoginRepository;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  UserMapper userMapper; // Inject UserMapper as a Bean
    @Autowired
    private LoginRepository loginRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);  // Use injected mapper
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    /**
     * Retrieves user profile details by user ID.
     * @param userId ID of the user.
     * @return Optional containing UserDTO or empty if user not found.
     */
    @Override
    public Optional<UserDTO> getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDTO); // Convert User entity to UserDTO if found
    }

    /**
     * Retrieves a paginated list of users.
     * @param pageable Pagination and sorting parameters.
     * @return Paginated list of UserDTOs.
     */
    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDTO); // Convert each User entity to UserDTO
    }



    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Prevent changes to email & username (handled in LoginService)
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        existingUser.setAddress(userDTO.getAddress());
        existingUser.setCurrency(userDTO.getCurrency());
        existingUser.setTimezone(userDTO.getTimezone());
        existingUser.setProfilePicture(userDTO.getProfilePicture());
        existingUser.setNotificationPreferences(userDTO.getNotificationPreferences());
        existingUser.setPreferredLanguage(userDTO.getPreferredLanguage());

        userRepository.save(existingUser);
        return userMapper.toDTO(existingUser);
    }


    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Login login = loginRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Login not found"));

        user.setDeleted(true);
        login.setDeleted(true);

        userRepository.save(user);
        loginRepository.save(login);
    }



    @Override
    public void restoreUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Login login = loginRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Login not found"));

        user.setDeleted(false);
        login.setDeleted(false);

        userRepository.save(user);
        loginRepository.save(login);
    }

}
