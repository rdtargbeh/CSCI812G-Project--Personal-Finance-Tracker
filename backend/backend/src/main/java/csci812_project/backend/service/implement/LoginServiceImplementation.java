package csci812_project.backend.service.implement;

import csci812_project.backend.dto.LoginDTO;
import csci812_project.backend.entity.Login;
import csci812_project.backend.mapper.LoginMapper;
import csci812_project.backend.repository.LoginRepository;
import csci812_project.backend.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImplementation implements LoginService {

    private final LoginRepository loginRepository;
    private final LoginMapper loginMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginDTO registerUser(String userName, String email, String password) {
        if (loginRepository.existsByEmail(email) || loginRepository.existsByUserName(userName)) {
            throw new RuntimeException("Username or Email already exists");
        }

        Login login = new Login();
        login.setUserName(userName);
        login.setEmail(email);
        login.setPassword(passwordEncoder.encode(password));  // Encrypt password
        login.setVerified(false);
        login.setDeleted(false);

        login = loginRepository.save(login);
        return loginMapper.toDTO(login);
    }

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
