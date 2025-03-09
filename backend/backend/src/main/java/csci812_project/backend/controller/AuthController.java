package csci812_project.backend.controller;

import csci812_project.backend.dto.UserDTO;
import csci812_project.backend.entity.Role;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.RoleType;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.security.JwtTokenProvider;
import csci812_project.backend.service.UserService;
import csci812_project.backend.utility.AuthResponse;
import csci812_project.backend.utility.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    // BUILD A REGISTER USER REST API
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.register(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    // BUILD A LOGIN REST API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // ✅ Authenticate user and generate token
            String jwt = userService.authenticate(loginRequest.getUserName(), loginRequest.getPassword());

            // ✅ Fetch user details
            User user = userRepository.findByUserName(loginRequest.getUserName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return ResponseEntity.ok(Map.of("token", jwt, "role", user.getRoles()));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", e.getMessage())); // ❌ Send error response for deleted users
        }
    }


//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String token = jwtTokenProvider.generateToken(loginRequest.getUserName());
//
//        // ✅ Fetch user roles from DB
//        User user = userRepository.findByUserName(loginRequest.getUserName())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + loginRequest.getUserName()));
//
//        String role = String.valueOf(user.getRoles().stream()
//                .map(Role::getRoleName)
//                .findFirst()
//                .orElse(RoleType.valueOf("USER"))); // Default role if none found
//
//        // ✅ Return token + role
//        return ResponseEntity.ok(Map.of("token", token, "role", role));
//    }



    @PostMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid verification token"));

        if (user.isVerified()) {
            return ResponseEntity.badRequest().body("Account already verified.");
        }

        user.setVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);

        return ResponseEntity.ok("Account verified successfully!");
    }



}
