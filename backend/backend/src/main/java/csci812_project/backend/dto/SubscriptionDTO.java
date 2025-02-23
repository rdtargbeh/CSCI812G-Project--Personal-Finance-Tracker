package csci812_project.backend.dto;

import csci812_project.backend.enums.SubscriptionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SubscriptionDTO {

    /** Unique subscription ID */
    private Long subscriptionId;
    private Long userId;
    /** Subscription name (e.g., "Netflix") */
    private String name;

    /** Monthly charge amount */
    private BigDecimal amount;

    /** Next billing date */
    private LocalDateTime nextBillingDate;

    /** Payment method (Bank Account, Credit Card, etc.) */
//    private String paymentMethod;
    private Long paymentMethodId;

    /** Auto-renew status */
    private boolean autoRenew;

    /** Subscription status */
    private SubscriptionStatus status;

    /** Timestamp for when the subscription was created */
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;


    // Constructor
    public SubscriptionDTO(){}
    public SubscriptionDTO(Long subscriptionId, String name, BigDecimal amount, LocalDateTime nextBillingDate, String paymentMethod,
                           boolean autoRenew, SubscriptionStatus status, LocalDateTime dateCreated, Long userId, Long paymentMethodId) {
        this.subscriptionId = subscriptionId;
        this.name = name;
        this.amount = amount;
        this.paymentMethodId = paymentMethodId;
        this.nextBillingDate = nextBillingDate;
//        this.paymentMethod = paymentMethod;
        this.autoRenew = autoRenew;
        this.status = status;
        this.dateCreated = dateCreated;
        this.userId = userId;
    }

    // Getter and Setter
    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {return amount;}
    public void setAmount(BigDecimal amount) {this.amount = amount;}
    public LocalDateTime getNextBillingDate() {return nextBillingDate;}
    public void setNextBillingDate(LocalDateTime nextBillingDate) {this.nextBillingDate = nextBillingDate;}
    public Long getPaymentMethodId() {return paymentMethodId;}
    public void setPaymentMethodId(Long paymentMethodId) {this.paymentMethodId = paymentMethodId;}

    public boolean isAutoRenew() {
        return autoRenew;
    }

    public void setAutoRenew(boolean autoRenew) {
        this.autoRenew = autoRenew;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {this.status = status;}

    public LocalDateTime getDateCreated() {return dateCreated;}

    public void setDateCreated(LocalDateTime dateCreated) {this.dateCreated = dateCreated;}

    public Long getUserId() {return userId;}

    public void setUserId(Long userId) {this.userId = userId;}

    public LocalDateTime getDateUpdated() {return dateUpdated;}

    public void setDateUpdated(LocalDateTime dateUpdated) {this.dateUpdated = dateUpdated;}
}

