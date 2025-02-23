package csci812_project.backend.mapper;

import csci812_project.backend.dto.LoginDTO;
import csci812_project.backend.entity.Login;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper {

    public LoginDTO toDTO(Login login) {
        if (login == null) return null;
        return LoginDTO.builder()
                .userId(login.getUserId())
                .userName(login.getUserName())
                .email(login.getEmail())
                .isVerified(login.isVerified())
                .isDeleted(login.isDeleted())
                .build();
    }

    public Login toEntity(LoginDTO loginDTO) {
        if (loginDTO == null) return null;
        return Login.builder()
                .userId(loginDTO.getUserId())
                .userName(loginDTO.getUserName())
                .email(loginDTO.getEmail())
                .isVerified(loginDTO.isVerified())
                .isDeleted(loginDTO.isDeleted())
                .build();
    }
}

