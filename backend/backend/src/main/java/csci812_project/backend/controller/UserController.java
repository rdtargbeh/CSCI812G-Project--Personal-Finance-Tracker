package csci812_project.backend.controller;

import csci812_project.backend.dto.UserDTO;
import csci812_project.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    // BUILD AN UPDATE USER REST API
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUpdate(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(userId, userDTO));
    }

    // BUILD A GET USER BY ID REST API
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        try {
            UserDTO user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // BUILD A GET ALL USERS REST API
    @GetMapping
    public Page<UserDTO> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return userService.getAllUsers(PageRequest.of(page, size));
    }

    // BUILD A DELETE USER REST API
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User has been deleted successfully.");
    }

    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<String> removeUser(@PathVariable Long userId) {
        userService.removeUser(userId);
        return ResponseEntity.ok("User has been deleted successfully.");
    }


    /**
     * Restores a previously soft-deleted user account.
     */
    @PutMapping("/{userId}/restore")
    public ResponseEntity<String> restoreDeletedUser(@PathVariable Long userId) {
        userService.restoreDeletedUser(userId);
        return ResponseEntity.ok("User has been restored successfully.");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        return userService.getUserProfile(authentication);
    }


    // Restore later    ++++++++++++++++++++++++++++++++++


//    @PostMapping("/login")
//    public ResponseEntity<UserDTO> login(@RequestParam String username, @RequestParam String password) {
//        return ResponseEntity.ok(userService.authenticate(username, password));
//    }

    //    @PostMapping
//    public UserDTO createUser(@RequestBody UserDTO userDTO) {
//        return userService.createUser(userDTO);
//    }

}

