package csci812_project.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogDTO {

    /** Unique log ID */
    private Long logId;

    /** User ID associated with the action */
    private Long userId;

    /** Action performed (e.g., 'CREATE_TRANSACTION', 'DELETE_ACCOUNT') */
    private String action;

    /** Name of the entity (table) that was affected */
    private String entity;

    /** ID of the affected record */
    private Long entityId;

    /** JSON representation of the record before update/delete */
    private String oldValue;

    /** JSON representation of the record after update */
    private String newValue;

    /** Timestamp for when the action was logged */
    private LocalDateTime timestamp;
}
