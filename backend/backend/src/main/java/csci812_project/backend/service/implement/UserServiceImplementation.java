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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
        user.setVerified(false);
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

//    public void sendVerificationEmail(User user) {
//        String verificationLink = "http://localhost:8080/api/auth/verify?token=" + user.getVerificationToken();
//        String emailBody = "Click the link to verify your account: " + verificationLink;
//
//        emailService.send(user.getEmail(), "Verify Your Account", emailBody); // ✅ Send email
//    }


    // Simple login authentication (replace later)
    public String authenticate(String username, String password, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User Not Found" + userId));

        // ✅ Use Spring Security's authentication system
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // ✅ Generate JWT token upon successful authentication
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

    // METHOD TO DELETE USER BUT STILL SAVE IN DATABASE USING IS_DELETE
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Soft Delete
        user.setDeleted(true); // ✅ Mark as deleted
        userRepository.save(user);

    }

    // PERMANENTLY DELETE USER FROM DATABASE
    public void removeUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.deleteById(userId); // ❌ PERMANENTLY deletes the user
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

    @Override
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        // ✅ Get logged-in user's username from JWT
        String username = authentication.getName();
        System.out.println("🔍 Authenticated User: " + username);

        // ✅ Fetch user details
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // ✅ Create response object
        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getUserId());
        response.put("username", user.getUserName());
        response.put("email", user.getEmail());
        response.put("roles", user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }

}
