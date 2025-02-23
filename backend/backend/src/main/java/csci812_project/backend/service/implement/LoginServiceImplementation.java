package csci812_project.backend.service.implement;

import csci812_project.backend.dto.LoginDTO;
import csci812_project.backend.entity.Login;
import csci812_project.backend.entity.User;
import csci812_project.backend.mapper.LoginMapper;
import csci812_project.backend.repository.LoginRepository;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImplementation implements LoginService {


    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;


    public LoginDTO register(LoginDTO loginDTO) {
        Login login = new Login();
        login.setUserName(loginDTO.getUserName());
        login.setEmail(loginDTO.getEmail());
        login.setPassword(passwordEncoder.encode(loginDTO.getPassword()));

        login = loginRepository.save(login); // ✅ Saves and generates `userId`

        // ✅ Create empty user profile linked to the login
        User user = new User();
        user.setUserId(login.getLoginId()); // ✅ Use same `userId`
        user.setLogin(login);

        userRepository.save(user); // ✅ Saves empty user profile

        return loginMapper.toDTO(login);
    }

//    @Override
//    public LoginDTO registerUser(String userName, String email, String password) {
//        if (loginRepository.existsByEmail(email) || loginRepository.existsByUserName(userName)) {
//            throw new RuntimeException("Username or Email already exists");
//        }
//
//        Login login = new Login();
//        login.setUserName(userName);
//        login.setEmail(email);
//        login.setPassword(passwordEncoder.encode(password));  // Encrypt password
//        login.setVerified(false);
//        login.setDeleted(false);
//
//        login = loginRepository.save(login);
//        return loginMapper.toDTO(login);
//    }

    @Override
    public boolean authenticateUser(String userName, String password) {
        Optional<Login> loginOpt = loginRepository.findByUserName(userName);
        if (loginOpt.isEmpty() || loginOpt.get().isDeleted()) return false;

        Login login = loginOpt.get();
        return passwordEncoder.matches(password, login.getPassword());
    }

    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        Login login = loginRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(oldPassword, login.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        login.setPassword(passwordEncoder.encode(newPassword));
        loginRepository.save(login);
        return true;
    }

    @Override
    public void requestPasswordReset(String email) {
        Login login = loginRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Send verification code via email (to be implemented)
    }

    @Override
    public boolean resetPassword(String email, String newPassword, String verificationCode) {
        Login login = loginRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify code (to be implemented)

        login.setPassword(passwordEncoder.encode(newPassword));
        loginRepository.save(login);
        return true;
    }

    @Override
    public void deleteLogin(Long userId) {
        Login login = loginRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Login not found"));

        login.setDeleted(true);
        loginRepository.save(login);
    }

    @Override
    public void restoreLogin(Long userId) {
        Login login = loginRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Login not found"));

        login.setDeleted(false);
        loginRepository.save(login);
    }
}
