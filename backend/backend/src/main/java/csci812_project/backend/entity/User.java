package csci812_project.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
@Builder
public class User {
    /**
     * Unique ID for the user (Primary Key).
     * Auto-incremented by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    /**
     * Unique username for login. Cannot be null or duplicate.
     */
    @Column(name = "user_name", unique = true, nullable = false, length = 50)
    @NotBlank(message = "Username is required")
    private String userName;

    /**
     * First name of the user. Required field.
     */
    @Column(name = "first_name", nullable = false, length = 30)
    @NotBlank(message = "First name is required")
    private String firstName;

    /**
     * Last name of the user. Required field.
     */
    @Column(name = "last_name", nullable = false, length = 30)
    @NotBlank(message = "Last name is required")
    private String lastName;

    /**
     * User's phone number. Optional field, stored as a string to support country codes.
     */
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    /**
     * Physical address of the user. Optional field.
     */
    @Column(name = "address", length = 100)
    private String address;

    /**
     * Preferred currency (ISO 4217 format, e.g., USD, EUR, GBP).
     * Defaults to "USD".
     */
    @Column(name = "currency", nullable = false, length = 3)
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must be a valid 3-letter ISO code")
    private String currency = "USD";

    /**
     * Preferred timezone for financial tracking.
     * Defaults to "UTC".
     */
    @Column(name = "timezone", nullable = false, length = 50)
    private String timezone = "UTC";

    /**
     * URL or Base64 string of the user's profile picture.
     */
    @Column(name = "profile_picture", columnDefinition = "TEXT")
    private String profilePicture;

    /**
     * User notification preferences stored in JSON format.
     */
    @Column(name = "notification_preferences", columnDefinition = "TEXT")
    private String notificationPreferences;

    /**
     * Preferred language for localization (e.g., "en", "fr").
     * Defaults to "en".
     */
    @Column(name = "preferred_language", nullable = false, length = 20)
    private String preferredLanguage = "en";

    /**
     * Soft delete flag to prevent accidental deletion.
     * Instead of removing a user, set this flag to TRUE.
     */
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    /**
     * Timestamp for when the user was created.
     * Automatically set when a new record is inserted.
     */
    @Column(name = "date_created", updatable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();

    /**
     * Timestamp for when the user was last updated.
     * Automatically updates on modification.
     */
    @Column(name = "date_updated")
    private LocalDateTime dateUpdated = LocalDateTime.now();

    /**
     * Lifecycle hook to update the timestamp before updating.
     */
    @PreUpdate
    protected void onUpdate() {
        this.dateUpdated = LocalDateTime.now();
    }
}
