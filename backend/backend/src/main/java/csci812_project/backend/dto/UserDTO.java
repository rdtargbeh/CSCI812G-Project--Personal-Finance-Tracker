package csci812_project.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
public class UserDTO {

    /** Unique user ID */
    private Long loginId;

    /** First and last name of the user */
    private String firstName;
    private String lastName;

    /** Phone number of the user */
    private String phoneNumber;

    /** Physical address of the user */
    private String address;

    /** Preferred currency (USD, EUR, etc.) */
    private String currency;

    /** Preferred timezone for financial tracking */
    private String timezone;

    /** Profile picture (URL or Base64) */
    private String profilePicture;

    /** Notification preferences stored in JSON format */
    private String notificationPreferences;

    /** Preferred language for localization */
    private String preferredLanguage;

    /** Indicates whether the user is deleted (soft delete) */
    private boolean isDeleted;

    /** Timestamp for when the user was created */
    private LocalDateTime dateCreated;

    /** Timestamp for when the user was last updated */
    private LocalDateTime dateUpdated;


    // Constructor
    public UserDTO(){}
    public UserDTO(Long loginId, String firstName, String lastName, String phoneNumber, String address, String currency,
                   String timezone, String profilePicture, String notificationPreferences, String preferredLanguage,
                   boolean isDeleted, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.loginId = loginId;
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
    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
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

