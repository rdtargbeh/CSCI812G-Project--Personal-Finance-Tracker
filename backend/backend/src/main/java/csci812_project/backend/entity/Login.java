package csci812_project.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "login")
@Builder
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
}

