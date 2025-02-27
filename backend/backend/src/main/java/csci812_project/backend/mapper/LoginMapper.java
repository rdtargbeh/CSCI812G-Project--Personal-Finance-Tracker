//package csci812_project.backend.mapper;
//
//import csci812_project.backend.dto.LoginDTO;
//import csci812_project.backend.entity.Login;
//import org.springframework.stereotype.Component;
//
//@Component
//public class LoginMapper {
//
//    public LoginDTO toDTO(Login login) {
//        if (login == null) return null;
//
//        LoginDTO dto = new LoginDTO();
//        dto.setLongId(login.getLoginId());
//        dto.setUserName(login.getUserName());
//        dto.setEmail(login.getEmail());
//        dto.setVerified(login.isVerified());
//        dto.setDeleted(login.isDeleted());
//        return dto;
//    }
//
//    public Login toEntity(LoginDTO loginDTO) {
//        if (loginDTO == null) return null;
//
//        Login login = new Login();
//        login.setLoginId(loginDTO.getLongId());
//        login.setUserName(loginDTO.getUserName());
//        login.setEmail(loginDTO.getEmail());
//        login.setVerified(loginDTO.isVerified());
//        login.setDeleted(loginDTO.isDeleted());
//        return login;
//    }
//}
//
