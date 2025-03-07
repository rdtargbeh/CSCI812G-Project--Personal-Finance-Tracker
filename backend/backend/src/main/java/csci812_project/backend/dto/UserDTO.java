package csci812_project.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
public class UserDTO {

    private Long userId;
    private String userName;
    private String email;
    private String password; // Should be handled securely, avoid exposing in responses.
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String currency = "USD";
    private String timezone;
    private String profilePicture;
    private String notificationPreferences;
    private String preferredLanguage;
    private boolean isDeleted;
    private boolean isVerified;
    private LocalDateTime lastLogin;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
//    private String verificationToken;


    // Constructor
    public UserDTO(){}

    public UserDTO(Long userId, String userName, String email, String password, String firstName, String lastName, String phoneNumber,
                   String address, String currency, String timezone, String profilePicture, String notificationPreferences,
                   String preferredLanguage, boolean isDeleted, boolean isVerified, LocalDateTime lastLogin, LocalDateTime dateCreated,
                   LocalDateTime dateUpdated, String verificationToken) {
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
        this.lastLogin = lastLogin;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
//        this.verificationToken = verificationToken;
    }

    // ✅ Overloaded Constructor (Matches the 4 parameters)
    public UserDTO(Long userId, String userName, String email, String currency) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.currency = currency;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
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

//    public String getVerificationToken() {
//        return verificationToken;
//    }
//
//    public void setVerificationToken(String verificationToken) {
//        this.verificationToken = verificationToken;
//    }
}

