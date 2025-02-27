//package csci812_project.backend.service.implement;
//
//import csci812_project.backend.dto.LoginDTO;
//import csci812_project.backend.security.JwtTokenProvider;
//import csci812_project.backend.service.AuthenticationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthenticationServiceImplementation implements AuthenticationService {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtTokenProvider jwtTokenProvider;
//
//    @Autowired
//    public AuthenticationServiceImplementation(AuthenticationManager authenticationManager,
//                                               JwtTokenProvider jwtTokenProvider) {
//        this.authenticationManager = authenticationManager;
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @Override
//    public String authenticateUser(LoginDTO loginDTO) {
//        // ✅ Authenticate user
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword())
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // ✅ Extract username from authentication
//        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//
//        // ✅ Pass username to generateToken() instead of Authentication
//        return jwtTokenProvider.generateToken(username);
//    }
//
//}
