package csci812_project.backend.service.implement;

import csci812_project.backend.dto.UserDTO;
import csci812_project.backend.entity.User;
import csci812_project.backend.mapper.UserMapper;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  UserMapper userMapper; // Inject UserMapper as a Bean
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
//   @Autowired
//    private JwtTokenProvider jwtTokenProvider;


    // METHOD TO CREATE / REGISTER USER
    @Override
    public UserDTO register(UserDTO userDTO) {
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());

        // ✅ Assign default values if missing
        user.setCurrency(userDTO.getCurrency() != null ? userDTO.getCurrency() : "USD");
        user.setTimezone(userDTO.getTimezone() != null ? userDTO.getTimezone() : "UTC");
        user.setPreferredLanguage(userDTO.getPreferredLanguage() != null ? userDTO.getPreferredLanguage() : "en");
        user.setDeleted(false);
        user.setVerified(false);
        user.setLastLogin(LocalDateTime.now());
        user.setDateCreated(LocalDateTime.now());
        user.setDateUpdated(LocalDateTime.now());

        user = userRepository.save(user);

        // ✅ Use the correct constructor to match the return statement
        return new UserDTO(user.getUserId(), user.getUserName(), user.getEmail(), user.getCurrency());
    }


    // Simple login authentication (replace later)
    @Override
    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return passwordEncoder.matches(password, user.getPassword()); // ✅ Verify password
    }


    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {

        // ✅ First, verify that the user exists in login
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found" + userId));

        // ✅ Update profile fields
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setAddress(userDTO.getAddress());
        user.setCurrency(userDTO.getCurrency());
        user.setTimezone(userDTO.getTimezone());
        user.setPreferredLanguage(userDTO.getPreferredLanguage());
        user.setProfilePicture(userDTO.getProfilePicture());
        user.setNotificationPreferences(userDTO.getNotificationPreferences());
        user.setPreferredLanguage(userDTO.getPreferredLanguage());

        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    /**
     * Retrieves user profile details by user ID.
     * @param userId ID of the user.
     * @return Optional containing UserDTO or empty if user not found.
     */
    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findByUserIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        return userMapper.toDTO(user);
    }

    /**
     * Retrieves a paginated list of users.
     * @param pageable Pagination and sorting parameters.
     * @return Paginated list of UserDTOs.
     */
    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findByIsDeletedFalse(pageable) // ✅ Fetch only non-deleted users
                .map(userMapper::toDTO); // Convert each User entity to UserDTO
    }

    // METHOD TO DELETE USER
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Soft Delete
        user.setDeleted(true); // ✅ Mark as deleted
        userRepository.save(user);

//        userRepository.deleteById(userId); // ❌ PERMANENTLY deletes the user
    }


    @Override
    public void restoreDeletedUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setDeleted(false);
        userRepository.save(user);
    }


}
