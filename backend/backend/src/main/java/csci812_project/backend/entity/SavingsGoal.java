package csci812_project.backend.entity;

import csci812_project.backend.enums.ContributionFrequency;
import csci812_project.backend.enums.PriorityLevel;
import csci812_project.backend.enums.SavingsGoalStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "savings_goals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavingsGoal {

    /**
     * Unique savings goal ID (Primary Key).
     * Auto-generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saving_id")
    private Long id;

    /**
     * Foreign Key linking the savings goal to a user.
     * Ensures that each savings goal belongs to a specific user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_savings_goal_user"))
    private User user;

    /**
     * Name of the savings goal (e.g., "Vacation Fund", "Emergency Savings").
     * Required field.
     */
    @Column(name = "goal_name", nullable = false, length = 100)
    @NotBlank(message = "Goal name is required")
    private String goalName;

    /**
     * Target savings amount for this goal.
     * Cannot be negative.
     */
    @Column(name = "target_amount", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.00", message = "Target amount cannot be negative")
    private BigDecimal targetAmount;

    /**
     * Current amount saved towards the goal.
     * Default is 0.00 and cannot be negative.
     */
    @Column(name = "current_amount", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.00", message = "Current amount cannot be negative")
    private BigDecimal currentAmount = BigDecimal.ZERO;

    /**
     * Deadline for achieving the savings goal.
     */
    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    /**
     * Status of the savings goal (ACTIVE, COMPLETED, CANCELLED).
     * Defaults to ACTIVE.
     */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SavingsGoalStatus status = SavingsGoalStatus.ACTIVE;

    /**
     * Indicates whether contributions to this goal should be automated.
     */
    @Column(name = "auto_save", nullable = false)
    private boolean autoSave = false;

    /**
     * Priority level for the savings goal (LOW, MEDIUM, HIGH).
     * Defaults to MEDIUM.
     */
    @Column(name = "priority_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private PriorityLevel priorityLevel = PriorityLevel.MEDIUM;

    /**
     * Frequency of contributions (DAILY, WEEKLY, MONTHLY, CUSTOM).
     * Defaults to MONTHLY.
     */
    @Column(name = "contribution_frequency", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContributionFrequency contributionFrequency = ContributionFrequency.MONTHLY;

    /**
     * Soft delete flag to prevent accidental deletion.
     * Instead of removing a savings goal, set this flag to TRUE.
     */
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    /**
     * Timestamp for when the savings goal was created.
     * Automatically set when a new record is inserted.
     */
    @Column(name = "date_created", updatable = false)
    private LocalDate dateCreated = LocalDate.now();

    /**
     * Timestamp for when the savings goal was last updated.
     * Automatically updates on modification.
     */
    @Column(name = "date_updated")
    private LocalDate dateUpdated = LocalDate.now();

    /**
     * Lifecycle hook to update the timestamp before updating.
     */
    @PreUpdate
    protected void onUpdate() {
        this.dateUpdated = LocalDate.now();
    }
}

