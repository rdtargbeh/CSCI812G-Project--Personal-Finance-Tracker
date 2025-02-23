package csci812_project.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    // Constructor
    public AccountDTO(){}
    public AccountDTO(Long accountId, Long userId, String name, String type, BigDecimal balance, String currency, String institutionName,
                      String accountNumber, BigDecimal interestRate, boolean isDefault, boolean isDeleted, LocalDateTime dateCreated,
                      LocalDateTime dateUpdated) {
        this.accountId = accountId;
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.currency = currency;
        this.institutionName = institutionName;
        this.accountNumber = accountNumber;
        this.interestRate = interestRate;
        this.isDefault = isDefault;
        this.isDeleted = isDeleted;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    // Getter and Setter

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
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
