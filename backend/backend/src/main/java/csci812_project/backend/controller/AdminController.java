package csci812_project.backend.controller;

import csci812_project.backend.dto.RoleAssignmentDTO;
import csci812_project.backend.dto.UserDTO;
import csci812_project.backend.entity.Role;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.RoleType;
import csci812_project.backend.repository.RoleRepository;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')") // ✅ Only Admins can access
public class AdminController {

    @Autowired
    private UserService userService;


//    @PostMapping("/register")
//    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
//        System.out.println("Received registration request for: " + userDTO.getUserName()); // ✅ Log request
//        UserDTO registeredUser = userService.register(userDTO);
//        return ResponseEntity.ok(registeredUser);
//    }
    @PutMapping("/assign-role")
    public ResponseEntity<String> assignRole(@RequestBody RoleAssignmentDTO request) {
        userService.assignRoleToUser(request.getUserId(), request.getRole());
        return ResponseEntity.ok("✅ Role assigned successfully!");
    }

//    @PostMapping("/assign-role")
//    public ResponseEntity<String> assignRole(@RequestBody RoleAssignmentDTO request) {
//        userService.assignRoleToUser(request.getUserId(), request.getRole());
//        return ResponseEntity.ok("✅ Role assigned successfully!");
//    }




//    // ✅ Assign a Role to a User
//    @PutMapping("/assign-role/{userId}")
//    public ResponseEntity<String> assignRole(@PathVariable Long userId, @RequestParam String roleName) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Role role = roleRepository.findByRoleName(RoleType.valueOf(roleName.toUpperCase()))
//                .orElseThrow(() -> new RuntimeException("Role not found"));
//
//        user.getRoles().add(role);
//        userRepository.save(user);
//        return ResponseEntity.ok("✅ Role " + roleName + " assigned to user ID " + userId);
//    }
//
//    // ✅ Remove a Role from a User
//    @PutMapping("/remove-role/{userId}")
//    public ResponseEntity<String> removeRole(@PathVariable Long userId, @RequestParam String roleName) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Role role = roleRepository.findByRoleName(RoleType.valueOf(roleName.toUpperCase()))
//                .orElseThrow(() -> new RuntimeException("Role not found"));
//
//        user.getRoles().remove(role);
//        userRepository.save(user);
//        return ResponseEntity.ok("✅ Role " + roleName + " removed from user ID " + userId);
//    }
}

