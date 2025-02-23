package csci812_project.backend.controller;

import csci812_project.backend.dto.LoginDTO;
import csci812_project.backend.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;


    /**
     * ✅ Registers a new user and creates an empty user profile.
     * @param loginDTO The login details (username, email, password).
     * @return The registered user's login details.
     */
    @PostMapping("/register")
    public ResponseEntity<LoginDTO> register(@RequestBody LoginDTO loginDTO) {
        LoginDTO registeredUser = loginService.register(loginDTO);
        return ResponseEntity.ok(registeredUser);
    }

//    @PostMapping("/register")
//    public ResponseEntity<LoginDTO> register(@RequestParam String userName,
//                                             @RequestParam String email,
//                                             @RequestParam String password) {
//        return ResponseEntity.ok(loginService.registerUser(userName, email, password));
//    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> authenticate(@RequestParam String userName,
                                                @RequestParam String password) {
        return ResponseEntity.ok(loginService.authenticateUser(userName, password));
    }

    /**
     * Changes the user's password (Requires old password)
     */
    @PutMapping("/{userId}/change-password")
    public ResponseEntity<String> changePassword(
            @PathVariable Long userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        boolean success = loginService.changePassword(userId, oldPassword, newPassword);
        return success
                ? ResponseEntity.ok("Password changed successfully.")
                : ResponseEntity.badRequest().body("Password change failed.");
    }

    /**
     * Requests a password reset (Sends a verification code)
     */
    @PostMapping("/request-password-reset")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
        loginService.requestPasswordReset(email);
        return ResponseEntity.ok("Password reset request successful. Check your email.");
    }

    /**
     * Resets the password using a verification code
     */
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String email,
            @RequestParam String newPassword,
            @RequestParam String verificationCode) {
        boolean success = loginService.resetPassword(email, newPassword, verificationCode);
        return success
                ? ResponseEntity.ok("Password reset successfully.")
                : ResponseEntity.badRequest().body("Invalid verification code.");
    }

    /**
     * Soft deletes a login (Disables the user's account)
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteLogin(@PathVariable Long userId) {
        loginService.deleteLogin(userId);
        return ResponseEntity.ok("Login account has been soft deleted.");
    }

    /**
     * Restores a soft-deleted login account
     */
    @PutMapping("/{userId}/restore")
    public ResponseEntity<String> restoreLogin(@PathVariable Long userId) {
        loginService.restoreLogin(userId);
        return ResponseEntity.ok("Login account has been restored.");
    }
}

