package csci812_project.backend.dto;

import csci812_project.backend.enums.PaymentMethod;
import csci812_project.backend.enums.TransactionStatus;
import csci812_project.backend.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Transaction DetailsDTO  to pass to generate Transaction report in report service
public class TransactionDetailsDTO {
    private BigDecimal amount;
    private TransactionType transactionType;
    private String description;
    private LocalDateTime date;
    private PaymentMethod paymentMethod;
    private LocalDate nextDueDate;
    private Long toAccountId;
    private LocalDateTime dateCreated;
    private TransactionStatus status;
    private String accountName; // From Account entity
    private String category; // From Category entity

    // Constructor
    public TransactionDetailsDTO(){}
    public TransactionDetailsDTO(BigDecimal amount, TransactionType transactionType, String description, LocalDateTime date,
                                 PaymentMethod paymentMethod, LocalDate nextDueDate, Long toAccountId, LocalDateTime dateCreated,
                                 TransactionStatus status, String accountName, String category) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.description = description;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.nextDueDate = nextDueDate;
        this.toAccountId = toAccountId;
        this.dateCreated = dateCreated;
        this.status = status;
        this.accountName = accountName;
        this.category = category;
    }

    // Getter and Setter

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(LocalDate nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
