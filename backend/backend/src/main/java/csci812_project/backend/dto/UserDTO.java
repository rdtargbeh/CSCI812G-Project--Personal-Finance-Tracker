package csci812_project.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    /** Unique user ID */
    private Long id;

    /** Unique username for login */
    private String userName;

    /** First and last name of the user */
    private String firstName;
    private String lastName;

    /** User's email address */
    private String email;

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
}

