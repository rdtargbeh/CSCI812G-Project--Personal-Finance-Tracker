package csci812_project.backend.dto;

import csci812_project.backend.enums.SubscriptionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public class SubscriptionDTO {

    /** Unique subscription ID */
    private Long id;

    /** Subscription name (e.g., "Netflix") */
    private String name;

    /** Monthly charge amount */
    private BigDecimal amount;

    /** Next billing date */
    private LocalDateTime nextBillingDate;

    /** Payment method (Bank Account, Credit Card, etc.) */
    private String paymentMethod;

    /** Auto-renew status */
    private boolean autoRenew;

    /** Subscription status */
    private SubscriptionStatus status;

    /** Timestamp for when the subscription was created */
    private LocalDateTime dateCreated;

    // Constructor
    public SubscriptionDTO(){}
    public SubscriptionDTO(Long id, String name, BigDecimal amount, LocalDateTime nextBillingDate, String paymentMethod,
                           boolean autoRenew, SubscriptionStatus status, LocalDateTime dateCreated) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.nextBillingDate = nextBillingDate;
        this.paymentMethod = paymentMethod;
        this.autoRenew = autoRenew;
        this.status = status;
        this.dateCreated = dateCreated;
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getNextBillingDate() {
        return nextBillingDate;
    }

    public void setNextBillingDate(LocalDateTime nextBillingDate) {
        this.nextBillingDate = nextBillingDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
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
}

