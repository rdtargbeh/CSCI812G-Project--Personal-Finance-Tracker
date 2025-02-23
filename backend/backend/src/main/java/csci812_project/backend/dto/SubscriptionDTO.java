package csci812_project.backend.dto;

import csci812_project.backend.enums.SubscriptionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}

