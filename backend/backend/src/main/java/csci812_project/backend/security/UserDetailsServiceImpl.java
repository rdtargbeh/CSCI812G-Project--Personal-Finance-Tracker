package csci812_project.backend.security;

import csci812_project.backend.entity.User;
import csci812_project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    // Modify loadUserByUsername() to reject unverified or deleted users
    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userNameOrEmail));

        if (!user.isVerified()) {
            throw new DisabledException("Account is not verified.");
        }

        if (user.isDeleted()) {
            throw new LockedException("Account is deleted.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                Collections.emptyList()
        );
    }



//    @Override
//    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
//
//        Login login = loginRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail));
//
//        return new org.springframework.security.core.userdetails.User(
//                login.getUserName(), // ✅ Get username from `Login` entity
//                login.getPassword(), // ✅ Get password from `Login` entity
//                Collections.emptyList() // ✅ Add roles/authorities if needed
//        );
//
//    }


}

