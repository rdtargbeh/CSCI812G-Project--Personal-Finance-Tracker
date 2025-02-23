package csci812_project.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "login")
public class Login {

    /** User ID (Primary Key, links to `users`) */
    @Id
    @Column(name = "user_id")
    private Long userId;

    /** Unique username for authentication */
    @Column(name = "user_name", unique = true, nullable = false, length = 50)
    @NotBlank(message = "Username is required")
    private String userName;

    /** User email (unique, required) */
    @Column(name = "email", unique = true, nullable = false, length = 50)
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    /** Hashed password */
    @Column(name = "password", nullable = false, length = 255)
    @NotBlank(message = "Password is required")
    private String password;

    /** Email verification status */
    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;

    /** Timestamp for the last login */
    @Column(name = "last_login")
    private LocalDateTime lastLogin = LocalDateTime.now();

    /** Soft delete flag */
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    /** Relationship to `User` (One-to-One) */
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    // Constructor
    public Login(){}
    public Login(Long userId, String userName, String email, String password, boolean isVerified, LocalDateTime lastLogin,
                 boolean isDeleted, User user) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.isVerified = isVerified;
        this.lastLogin = lastLogin;
        this.isDeleted = isDeleted;
        this.user = user;
    }

    // Getter and Setter

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public @NotBlank(message = "Username is required") String getUserName() {
        return userName;
    }

    public void setUserName(@NotBlank(message = "Username is required") String userName) {
        this.userName = userName;
    }

    public @Email(message = "Invalid email format") @NotBlank(message = "Email is required") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Invalid email format") @NotBlank(message = "Email is required") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

