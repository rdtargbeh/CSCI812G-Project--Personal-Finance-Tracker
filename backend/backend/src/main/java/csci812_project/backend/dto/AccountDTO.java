package csci812_project.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {

    /** Unique account ID */
    private Long accountId;

    /** User ID associated with the account */
    private Long userId;

    /** Name of the account (e.g., "Savings Account", "Personal Wallet") */
    private String name;

    /** Type of the account (BANK, CASH, CREDIT_CARD, WALLET) */
    private String type;

    /** Current balance in the account */
    private BigDecimal balance;

    /** Preferred currency for the account (e.g., USD, EUR) */
    private String currency;

    /** Financial institution name (if applicable) */
    private String institutionName;

    /** Account number (masked for security if needed) */
    private String accountNumber;

    /** Interest rate applied to the account */
    private BigDecimal interestRate;

    /** Indicates whether this is the default account */
    private boolean isDefault;

    /** Indicates whether the account is deleted (soft delete) */
    private boolean isDeleted;

    /** Timestamp for when the account was created */
    private LocalDateTime dateCreated;

    /** Timestamp for when the account was last updated */
    private LocalDateTime dateUpdated;
}
