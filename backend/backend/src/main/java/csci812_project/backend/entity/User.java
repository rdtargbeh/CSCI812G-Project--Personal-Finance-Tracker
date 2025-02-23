package csci812_project.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
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

    // Constructor
    public User(){}
    public User(Long userId, String userName, String firstName, String lastName, String phoneNumber, String address, String currency,
                String timezone, String profilePicture, String notificationPreferences, String preferredLanguage, boolean isDeleted,
                LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.currency = currency;
        this.timezone = timezone;
        this.profilePicture = profilePicture;
        this.notificationPreferences = notificationPreferences;
        this.preferredLanguage = preferredLanguage;
        this.isDeleted = isDeleted;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
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

    public @NotBlank(message = "First name is required") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "First name is required") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Last name is required") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Last name is required") String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public @Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must be a valid 3-letter ISO code") String getCurrency() {
        return currency;
    }

    public void setCurrency(@Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must be a valid 3-letter ISO code") String currency) {
        this.currency = currency;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getNotificationPreferences() {
        return notificationPreferences;
    }

    public void setNotificationPreferences(String notificationPreferences) {
        this.notificationPreferences = notificationPreferences;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
