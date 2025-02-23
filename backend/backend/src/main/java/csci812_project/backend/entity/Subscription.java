package csci812_project.backend.entity;

import csci812_project.backend.enums.SubscriptionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    /**
     * Unique subscription ID (Primary Key).
     * Auto-generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Long subscriptionId;

    /**
     * Foreign Key linking the subscription to a user.
     * Ensures that each subscription is assigned to a specific user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_subscription_user"))
    private User user;

    /**
     * Subscription name (e.g., "Netflix", "Gym Membership").
     * Required field.
     */
    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "Subscription name is required")
    private String name;

    /**
     * Monthly charge amount for the subscription.
     * Cannot be negative.
     */
    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.00", message = "Amount cannot be negative")
    private BigDecimal amount;

    /**
     * Date of the next billing cycle.
     * Must be in the present or future.
     */
    @Column(name = "next_billing_date", nullable = false)
    @FutureOrPresent(message = "Next billing date must be today or in the future")
    private LocalDateTime nextBillingDate;

    /**
     * Foreign Key linking to the payment method (Account).
     */
    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false, foreignKey = @ForeignKey(name = "fk_subscription_payment"))
    private Account paymentMethod;

    /**
     * Indicates whether the subscription auto-renews.
     */
    @Column(name = "auto_renew", nullable = false)
    private boolean autoRenew = true;

    /**
     * Status of the subscription (ACTIVE, CANCELLED, PAUSED).
     * Defaults to ACTIVE.
     */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status = SubscriptionStatus.ACTIVE;

    /**
     * Timestamp for when the subscription was created.
     * Automatically set when a new record is inserted.
     */
    @Column(name = "date_created", updatable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();

    /**
     * Timestamp for when the subscription was last updated.
     * Automatically updates on modification.
     */
    @Column(name = "date_updated")
    private LocalDateTime dateUpdated = LocalDateTime.now();

    /**
     * Lifecycle hook to update the timestamp before updating.
     */
    @PreUpdate
    protected void onUpdate() {
        this.dateUpdated = LocalDateTime.now();
    }

    // Constructor
    public Subscription(){}
    public Subscription(Long subscriptionId, User user, String name, BigDecimal amount, LocalDateTime nextBillingDate, Account paymentMethod,
                        boolean autoRenew, SubscriptionStatus status, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.subscriptionId = subscriptionId;
        this.user = user;
        this.name = name;
        this.amount = amount;
        this.nextBillingDate = nextBillingDate;
        this.paymentMethod = paymentMethod;
        this.autoRenew = autoRenew;
        this.status = status;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    // Getter and Setter

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public @NotBlank(message = "Subscription name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Subscription name is required") String name) {
        this.name = name;
    }

    public @DecimalMin(value = "0.00", message = "Amount cannot be negative") BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@DecimalMin(value = "0.00", message = "Amount cannot be negative") BigDecimal amount) {
        this.amount = amount;
    }

    public @FutureOrPresent(message = "Next billing date must be today or in the future") LocalDateTime getNextBillingDate() {
        return nextBillingDate;
    }

    public void setNextBillingDate(@FutureOrPresent(message = "Next billing date must be today or in the future") LocalDateTime nextBillingDate) {
        this.nextBillingDate = nextBillingDate;
    }

    public Account getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Account paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isAutoRenew() {
        return autoRenew;
    }

    public void setAutoRenew(boolean autoRenew) {
        this.autoRenew = autoRenew;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
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

