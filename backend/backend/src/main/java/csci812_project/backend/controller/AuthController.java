//package csci812_project.backend.controller;
//
//import csci812_project.backend.dto.LoginDTO;
//import csci812_project.backend.service.AuthenticationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    private final AuthenticationService authenticationService;
//
//    @Autowired
//    public AuthController(AuthenticationService authenticationService) {
//        this.authenticationService = authenticationService;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO) {
//        String token = authenticationService.authenticateUser(loginDTO);
//        return ResponseEntity.ok(token);
//    }
//}
