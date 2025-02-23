package csci812_project.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {
    private Long userId;
    private String userName;
    private String email;
    private boolean isVerified;
    private boolean isDeleted;
}

