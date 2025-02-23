package csci812_project.backend.dto;

import java.time.LocalDateTime;

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


    // Constructor
    public AuditLogDTO(){}
    public AuditLogDTO(Long logId, Long userId, String action, String entity, Long entityId, String oldValue,
                       String newValue, LocalDateTime timestamp) {
        this.logId = logId;
        this.userId = userId;
        this.action = action;
        this.entity = entity;
        this.entityId = entityId;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.timestamp = timestamp;
    }

    // Getter and Setter

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
