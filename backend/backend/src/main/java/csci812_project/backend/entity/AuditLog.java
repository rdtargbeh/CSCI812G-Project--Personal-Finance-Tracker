package csci812_project.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "audit_logs")
@Builder
public class AuditLog {

    /**
     * Unique log ID (Primary Key).
     * Auto-generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    /**
     * Foreign Key linking the log entry to a user.
     * Ensures that each log entry belongs to a specific user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_audit_log_user"))
    private User user;

    /**
     * The action performed (e.g., 'CREATE_TRANSACTION', 'DELETE_ACCOUNT').
     */
    @Column(name = "action", nullable = false, length = 255)
    @NotBlank(message = "Action description is required")
    private String action;

    /**
     * The name of the entity (table) that was affected.
     */
    @Column(name = "entity", nullable = false, length = 255)
    @NotBlank(message = "Entity name is required")
    private String entity;

    /**
     * The ID of the record that was affected.
     */
    @Column(name = "entity_id", nullable = false)
    @NotNull(message = "Entity ID is required")
    private Long entityId;

    /**
     * The previous state of the record before modification (stored as JSON).
     * Can be null if no previous value exists.
     */
    @Column(name = "old_value", columnDefinition = "TEXT")
    private String oldValue;

    /**
     * The new state of the record after modification (stored as JSON).
     * Can be null if the record was deleted.
     */
    @Column(name = "new_value", columnDefinition = "TEXT")
    private String newValue;

    /**
     * Timestamp for when the action was logged.
     * Automatically set when a new record is inserted.
     */
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
}
