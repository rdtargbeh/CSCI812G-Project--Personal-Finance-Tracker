package csci812_project.backend.mapper;

import csci812_project.backend.dto.UserDTO;
import csci812_project.backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    /**
     * Convert User entity to UserDTO.
     * @param user The User entity to be converted.
     * @return UserDTO representing the User entity.
     */
    public UserDTO toDTO(User user) {
        if (user == null) return null;
        return UserDTO.builder()
                .userId(user.getUserId())  // Changed from 'id' to 'userId'
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .currency(user.getCurrency())
                .timezone(user.getTimezone())
                .profilePicture(user.getProfilePicture())
                .notificationPreferences(user.getNotificationPreferences())
                .preferredLanguage(user.getPreferredLanguage())
                .isDeleted(user.isDeleted())
                .dateCreated(user.getDateCreated())
                .dateUpdated(user.getDateUpdated())
                .build();
    }

    /**
     * Convert UserDTO to User entity.
     * @param userDTO The UserDTO to be converted.
     * @return User entity representing the UserDTO.
     */
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        return User.builder()
                .userId(userDTO.getUserId())  // Changed from 'id' to 'userId'
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .phoneNumber(userDTO.getPhoneNumber())
                .address(userDTO.getAddress())
                .currency(userDTO.getCurrency())
                .timezone(userDTO.getTimezone())
                .profilePicture(userDTO.getProfilePicture())
                .notificationPreferences(userDTO.getNotificationPreferences())
                .preferredLanguage(userDTO.getPreferredLanguage())
                .isDeleted(userDTO.isDeleted())
                .dateCreated(userDTO.getDateCreated())
                .dateUpdated(userDTO.getDateUpdated())
                .build();
    }
}

