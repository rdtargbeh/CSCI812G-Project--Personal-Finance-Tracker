package csci812_project.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public class TransactionDTO {

    /** Unique transaction ID */
    private Long transactionId;

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
    private boolean isRecurring;

    private String recurringInterval;
    private LocalDateTime nextDueDate;

    /** Parent transaction ID (if part of a split or related transaction) */
    private Long parentTransactionId;

    /** Status of the transaction (PENDING, COMPLETED, FAILED) */
    private String status;
    private Long toAccountId; // Added for transfers

    /** Indicates whether the transaction is deleted (soft delete) */
    private boolean isDeleted;

    /** Timestamp for when the transaction was created */
    private LocalDateTime dateCreated;

    /** Timestamp for when the transaction was last updated */
    private LocalDateTime dateUpdated;

    // Constructor
    public TransactionDTO(){}
    public TransactionDTO(Long transactionId, Long userId, Long accountId, Long categoryId, BigDecimal amount, String transactionType,
                          LocalDateTime date, String description, String paymentMethod, String receiptUrl, boolean isRecurring,
                          String recurringInterval, LocalDateTime nextDueDate, Long parentTransactionId, String status,
                          Long toAccountId, boolean isDeleted, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.date = date;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.receiptUrl = receiptUrl;
        this.isRecurring = isRecurring;
        this.recurringInterval = recurringInterval;
        this.nextDueDate = nextDueDate;
        this.parentTransactionId = parentTransactionId;
        this.status = status;
        this.toAccountId = toAccountId;
        this.isDeleted = isDeleted;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    // Getter and Setter
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

    public String getRecurringInterval() {
        return recurringInterval;
    }

    public void setRecurringInterval(String recurringInterval) {
        this.recurringInterval = recurringInterval;
    }

    public LocalDateTime getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(LocalDateTime nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public Long getParentTransactionId() {
        return parentTransactionId;
    }

    public void setParentTransactionId(Long parentTransactionId) {
        this.parentTransactionId = parentTransactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
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
