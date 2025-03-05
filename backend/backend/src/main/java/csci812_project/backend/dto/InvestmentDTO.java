package csci812_project.backend.dto;

import csci812_project.backend.enums.InvestmentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvestmentDTO {

    /** Unique investment ID */
    private Long investmentId;
    private BigDecimal performance;
    private Long userId;

    /** Type of investment (STOCKS, CRYPTO, MUTUAL_FUNDS, REAL_ESTATE) */
    private InvestmentType investmentType;

    /** Name of the investment asset (e.g., "Apple Stocks", "Bitcoin") */
    private String assetName;

    /** Initial amount invested */
    private BigDecimal amountInvested;

    /** Current market value of the investment */
    private BigDecimal currentValue;

    /** Date of purchase */
    private LocalDateTime purchaseDate;

    private boolean isDeleted;
    /** Last updated timestamp */
    private LocalDateTime lastUpdated;

    /** Timestamp for when the investment record was created */
    private LocalDateTime dateCreated;

    // Constructor
    public InvestmentDTO(){}

    public InvestmentDTO(Long investmentId, Long userId, InvestmentType investmentType, String assetName, BigDecimal amountInvested,
                         BigDecimal currentValue, LocalDateTime purchaseDate, boolean isDeleted, LocalDateTime lastUpdated,
                         LocalDateTime dateCreated, BigDecimal performance) {
        this.investmentId = investmentId;
        this.userId = userId;
        this.investmentType = investmentType;
        this.assetName = assetName;
        this.amountInvested = amountInvested;
        this.currentValue = currentValue;
        this.purchaseDate = purchaseDate;
        this.isDeleted = isDeleted;
        this.lastUpdated = lastUpdated;
        this.dateCreated = dateCreated;
        this.performance = performance;
    }

    // Getter and Setter

    public Long getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Long investmentId) {
        this.investmentId = investmentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public InvestmentType getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(InvestmentType investmentType) {
        this.investmentType = investmentType;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public BigDecimal getAmountInvested() {
        return amountInvested;
    }

    public void setAmountInvested(BigDecimal amountInvested) {
        this.amountInvested = amountInvested;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public BigDecimal getPerformance() {
        return performance;
    }

    public void setPerformance(BigDecimal performance) {
        this.performance = performance;
    }
}
