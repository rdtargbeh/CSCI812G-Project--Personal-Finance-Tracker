package csci812_project.backend.dto;

import lombok.*;

@Builder
public class LoginDTO {
    private Long longId;
    private String userName;
    private String email;
    private boolean isVerified;
    private boolean isDeleted;
    private String password;


    // Constructor
    public LoginDTO(){}
    public LoginDTO(Long longId, String userName, String email, boolean isVerified, boolean isDeleted, String password) {
        this.longId = longId;
        this.userName = userName;
        this.email = email;
        this.isVerified = isVerified;
        this.isDeleted = isDeleted;
        this.password = password;
    }

    // Getter and Setter

    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long longId) {
        this.longId = longId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public boolean isVerified() {return isVerified;}
    public void setVerified(boolean verified) {isVerified = verified;}

    public boolean isDeleted() {return isDeleted;}

    public void setDeleted(boolean deleted) {isDeleted = deleted;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}


}

