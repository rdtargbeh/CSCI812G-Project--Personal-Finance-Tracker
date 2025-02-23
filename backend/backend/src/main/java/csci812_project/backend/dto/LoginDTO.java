package csci812_project.backend.dto;

import lombok.*;

@Builder
public class LoginDTO {
    private Long userId;
    private String userName;
    private String email;
    private boolean isVerified;
    private boolean isDeleted;


    // Constructor
    public LoginDTO(){}
    public LoginDTO(Long userId, String userName, String email, boolean isVerified, boolean isDeleted) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.isVerified = isVerified;
        this.isDeleted = isDeleted;
    }

    // Getter and Setter

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}

