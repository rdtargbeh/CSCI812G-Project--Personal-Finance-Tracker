package csci812_project.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    /**
     * Unique ID for the user (Primary Key).
     * Non Auto-incremented by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
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

    /**
     * First name of the user. Required field.
     */
    @Column(name = "first_name", length = 30)
    @NotBlank(message = "First name is required")
    private String firstName;

    /**
     * Last name of the user. Required field.
     */
    @Column(name = "last_name", length = 30)
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

    /** Email verification status */
    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;

    /** Timestamp for the last login */
    @Column(name = "last_login")
    private LocalDateTime lastLogin = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",  // ✅ Join table to handle M:N relation
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

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

    // Store verification token
    @Column(name = "verification_token")
    private String verificationToken;

    /**
     * Lifecycle hook to update the timestamp before updating.
     */
    @PreUpdate
    protected void onUpdate() {
        this.dateUpdated = LocalDateTime.now();
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                '}';
    }


    // Constructor
    public User(){}

    public User(Long userId, String userName, String email, String password, String firstName, String lastName, String phoneNumber,
                String address, String currency, String timezone, String profilePicture, String notificationPreferences,
                String preferredLanguage, boolean isDeleted, boolean isVerified, LocalDateTime lastLogin, LocalDateTime dateCreated,
                LocalDateTime dateUpdated, Set<Role> roles, String verificationToken) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
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
        this.isVerified = isVerified;
        this.roles = roles;
        this.lastLogin = lastLogin;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.verificationToken = verificationToken;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }
}
