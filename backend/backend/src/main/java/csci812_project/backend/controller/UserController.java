package csci812_project.backend.controller;

import csci812_project.backend.dto.UserDTO;
import csci812_project.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    // BUILD A REGISTER USER REST API
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.register(userDTO));
    }

    // BUILD A LOGIN REST API
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        boolean isAuthenticated = userService.authenticate(userDTO.getUserName(), userDTO.getPassword());

        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestParam String userName, @RequestParam String password) {
//        boolean isAuthenticated = userService.authenticate(userName, password);
//
//        if (isAuthenticated) {
//            return ResponseEntity.ok("Login successful!");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
//        }
//    }

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
    public ResponseEntity<String> deleteBudget(@PathVariable Long userId) {
        userService.deleteUser(userId);
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

