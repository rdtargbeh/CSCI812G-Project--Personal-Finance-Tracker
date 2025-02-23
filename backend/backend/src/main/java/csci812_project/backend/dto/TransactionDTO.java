package csci812_project.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {

    /** Unique transaction ID */
    private Long id;

    /** User ID associated with the transaction */
    private Long userId;

    /** Account ID from which the transaction was made */
    private Long accountId;

    /** Category ID to classify the transaction */
    private Long categoryId;

    /** Transaction amount */
    private BigDecimal amount;

    /** Type of transaction (INCOME, EXPENSE, TRANSFER) */
    private String transactionType;

    /** Date and time of the transaction */
    private LocalDateTime date;

    /** Description of the transaction */
    private String description;

    /** Payment method used (CASH, CREDIT_CARD, BANK_TRANSFER, PAYPAL) */
    private String paymentMethod;

    /** URL to a receipt or proof of transaction */
    private String receiptUrl;

    /** Indicates whether the transaction is recurring */
    private boolean recurring;

    /** Parent transaction ID (if part of a split or related transaction) */
    private Long parentTransactionId;

    /** Status of the transaction (PENDING, COMPLETED, FAILED) */
    private String status;

    /** Indicates whether the transaction is deleted (soft delete) */
    private boolean isDeleted;

    /** Timestamp for when the transaction was created */
    private LocalDateTime dateCreated;

    /** Timestamp for when the transaction was last updated */
    private LocalDateTime dateUpdated;
}
