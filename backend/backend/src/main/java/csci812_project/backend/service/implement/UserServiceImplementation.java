package csci812_project.backend.service.implement;

import csci812_project.backend.dto.UserDTO;
import csci812_project.backend.entity.Role;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.RoleType;
import csci812_project.backend.exception.NotFoundException;
import csci812_project.backend.mapper.UserMapper;
import csci812_project.backend.repository.RoleRepository;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.security.JwtTokenProvider;
import csci812_project.backend.service.EmailService;
import csci812_project.backend.service.UserService;
import csci812_project.backend.utility.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    EmailService emailService;
    @Autowired
    private FileStorageService fileStorageService; // ✅ Service for storing files




    // METHOD TO CREATE / REGISTER USER
    @Override
    public UserDTO register(UserDTO userDTO) {
        System.out.println("Registering user: " + userDTO.getUserName()); // ✅ Log user registration start
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

        user.setVerificationToken(UUID.randomUUID().toString()); // ✅ Generate unique token
        user.setDeleted(false);
        user.setVerified(true);
        user.setLastLogin(LocalDateTime.now());
        user.setDateCreated(LocalDateTime.now());
        user.setDateUpdated(LocalDateTime.now());

        user = userRepository.save(user);
        try {
            sendVerificationEmail(user); // ✅ Send email
            System.out.println("Verification email sent successfully!");
        } catch (Exception e) {
            System.err.println("Error sending verification email: " + e.getMessage()); // 🚨 Log email error
        }
//        sendVerificationEmail(user); // ✅ Send email
        // ✅ Use the correct constructor to match the return statement
        return new UserDTO(user.getUserId(), user.getUserName(), user.getEmail(), user.getCurrency());
    }


    public void sendVerificationEmail(User user) {
        String verificationLink = "http://localhost:8080/api/auth/verify?token=" + user.getVerificationToken();
        String emailBody = "Click the link to verify your account: " + verificationLink;
        try {
            emailService.sendEmail(user.getEmail(), "Verify Your Account", emailBody); // ✅ Send email
            System.out.println("✅ Verification email sent to: " + user.getEmail());
        } catch (Exception e) {
            System.err.println("🚨 Error in sendVerificationEmail: " + e.getMessage()); // 🚨 Log email error
        }
    }

    // Simple login authentication (replace later)
    @Override
    public String authenticate(String username, String password) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new NotFoundException("User Not Found: " + username));

        // ❌ Prevent deleted users from logging in
        if (user.isDeleted()) {
            throw new RuntimeException("Your account has been deleted. Contact support for help.");
        }
        // ✅ Authenticate with Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // ✅ Generate JWT Token
        return jwtTokenProvider.generateToken(username);
    }


    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {

        // ✅ First, verify that the user exists in login
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User Not Found" + userId));

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
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
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

    // PERMANENTLY DELETE USER FROM DATABASE
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.deleteById(userId); // ❌ PERMANENTLY deletes the user
    }

    // METHOD TO DELETE USER BUT STILL SAVE IN DATABASE USING IS_DELETE
    public void removeUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        // Soft Delete
        user.setDeleted(true); // ✅ Mark as deleted
        userRepository.save(user);
    }


    @Override
    public void restoreDeletedUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setDeleted(false);
        userRepository.save(user);
    }


    // ASSIGN ROLE TO USER
    @Override
    @Transactional
    public void assignRoleToUser(Long userId, RoleType roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        user.getRoles().add(role);
        userRepository.save(user);
    }


    /**
     * ✅ Get user profile by authenticated user (Ensures only the logged-in user accesses their profile)
     */
    public UserDTO getUserProfile(Authentication authentication) {
        String username = authentication.getName(); // Get username from JWT token

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));
        UserDTO userDTO = userMapper.toDTO(user); // Convert user entity to DTO
        // ✅ Ensure profile picture is never null
        if (userDTO.getProfilePicture() == null || userDTO.getProfilePicture().isEmpty()) {
            userDTO.setProfilePicture("http://localhost:8080/uploads/default-profile.png");
        }
        return userDTO;
//        return userMapper.toDTO(user); // Convert user entity to DTO
    }

    /**
     * ✅ Update user profile (Only editable fields can be changed)
     */
    public UserDTO updateUserProfile(Authentication authentication, UserDTO userDTO) {
        String username = authentication.getName(); // Get logged-in username

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));

        // ✅ Update only provided fields
        Optional.ofNullable(userDTO.getFirstName()).ifPresent(user::setFirstName);
        Optional.ofNullable(userDTO.getLastName()).ifPresent(user::setLastName);
        Optional.ofNullable(userDTO.getPhoneNumber()).ifPresent(user::setPhoneNumber);
        Optional.ofNullable(userDTO.getAddress()).ifPresent(user::setAddress);
        Optional.ofNullable(userDTO.getPreferredLanguage()).ifPresent(user::setPreferredLanguage);
        Optional.ofNullable(userDTO.getTimezone()).ifPresent(user::setTimezone);
        Optional.ofNullable(userDTO.getCurrency()).ifPresent(user::setCurrency);

        userRepository.save(user);
        return userMapper.toDTO(user); // Return updated user data
    }

    /**
     * ✅ Upload and update user's profile picture
     */
    public String uploadProfilePicture(MultipartFile file, Authentication authentication) {
        String username = authentication.getName(); // Get logged-in user's username

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));
        try {
            // ✅ Store image and get file URL
            String fileUrl = "http://localhost:8080/uploads/" + fileStorageService.storeFile(file);
            // ✅ Update user's profile picture URL
            user.setProfilePicture(fileUrl);
            userRepository.save(user);

            return fileUrl; // ✅ Return the file URL for frontend display
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

}
