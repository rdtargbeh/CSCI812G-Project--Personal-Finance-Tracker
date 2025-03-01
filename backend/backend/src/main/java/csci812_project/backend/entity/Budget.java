package csci812_project.backend.entity;

import csci812_project.backend.enums.BudgetType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "budgets")
public class Budget {

    /**
     * Unique budget ID (Primary Key).
     * Auto-generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long budgetId;

    /**
     * Foreign Key linking the budget to a user.
     * Ensures that each budget is assigned to a specific user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_budget_user"))
    private User user;

    /**
     * Foreign Key linking the budget to a category.
     * Allows budgets to be set for specific spending categories.
     */
    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_budget_category"))
    private Category category;

    @Column(name = "description", length = 30)
    private String description;
    /**
     * Maximum spending amount allowed within the budget period.
     * Cannot be negative.
     */
    @Column(name = "amount_limit", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.00", message = "Budget limit cannot be negative")
    private BigDecimal amountLimit;

    /**
     * Start date for the budget period.
     */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /**
     * End date for the budget period.
     */
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    /**
     * Type of budget (STRICT or FLEXIBLE).
     * Defaults to FLEXIBLE.
     */
    @Column(name = "budget_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BudgetType budgetType = BudgetType.FLEXIBLE;

    /**
     * Amount that rolls over to the next period (if applicable).
     * Cannot be negative.
     */
    @Column(name = "rollover_amount", precision = 15, scale = 2)
    @DecimalMin(value = "0.00", message = "Rollover amount cannot be negative")
    private BigDecimal rolloverAmount;

    /**
     * Soft delete flag to prevent accidental deletion.
     * Instead of removing a budget, set this flag to TRUE.
     */
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    /**
     * Timestamp for when the budget was created.
     * Automatically set when a new record is inserted.
     */
    @Column(name = "date_created", updatable = false)
    private LocalDate dateCreated = LocalDate.now();

    /**
     * Timestamp for when the budget was last updated.
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

    // Constructor
    public Budget(){}
    public Budget(Long budgetId, User user, Category category, BigDecimal amountLimit, LocalDate startDate, LocalDate endDate,
                  BudgetType budgetType, BigDecimal rolloverAmount, boolean isDeleted, LocalDate dateCreated, LocalDate dateUpdated, String description) {
        this.budgetId = budgetId;
        this.user = user;
        this.category = category;
        this.amountLimit = amountLimit;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budgetType = budgetType;
        this.rolloverAmount = rolloverAmount;
        this.isDeleted = isDeleted;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.description = description;
    }

    // Getter and Setter
    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public @DecimalMin(value = "0.00", message = "Budget limit cannot be negative") BigDecimal getAmountLimit() {
        return amountLimit;
    }

    public void setAmountLimit(@DecimalMin(value = "0.00", message = "Budget limit cannot be negative") BigDecimal amountLimit) {
        this.amountLimit = amountLimit;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BudgetType getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(BudgetType budgetType) {
        this.budgetType = budgetType;
    }

    public @DecimalMin(value = "0.00", message = "Rollover amount cannot be negative") BigDecimal getRolloverAmount() {
        return rolloverAmount;
    }

    public void setRolloverAmount(@DecimalMin(value = "0.00", message = "Rollover amount cannot be negative") BigDecimal rolloverAmount) {
        this.rolloverAmount = rolloverAmount;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
