package csci812_project.backend.security;

import csci812_project.backend.entity.Role;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.RoleType;
import csci812_project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userNameOrEmail));

        if (!user.isVerified()) {
            throw new DisabledException("Account is not verified. Please check your email.");
        }

        if (user.isDeleted()) {
            throw new LockedException("Account is deleted.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()) // ✅ Assign roles dynamically
        );
    }

    // ✅ Convert Roles into GrantedAuthorities
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName().name()));

            // ✅ SYSTEM_ADMIN automatically gets CRUD_ALL
            if (role.getRoleName().equals("SYSTEM_ADMIN")) {
                authorities.add(new SimpleGrantedAuthority("CRUD_ALL"));
            }
        }

        return authorities;
    }

}

