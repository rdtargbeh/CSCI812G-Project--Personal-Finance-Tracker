package csci812_project.backend.dto;

import csci812_project.backend.enums.InvestmentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

//  Investment DetailsDTO  to pass to generate investment report in report service
public class InvestmentDetailsDTO {
    private InvestmentType investmentType;
    private String assetName;
    private BigDecimal amountInvested;
    private BigDecimal currentValue;
    private LocalDate purchaseDate;
    private LocalDateTime lastUpdated;
    private BigDecimal performance;


    // Constructor
    public InvestmentDetailsDTO(){}
    public InvestmentDetailsDTO(InvestmentType investmentType, String assetName, BigDecimal amountInvested, BigDecimal currentValue,
                                LocalDate purchaseDate, LocalDateTime lastUpdated, BigDecimal performance) {
        this.investmentType = investmentType;
        this.assetName = assetName;
        this.amountInvested = amountInvested;
        this.currentValue = currentValue;
        this.purchaseDate = purchaseDate;
        this.lastUpdated = lastUpdated;
        this.performance = performance;
    }

    // Getter and Setter

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

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public BigDecimal getPerformance() {
        return performance;
    }

    public void setPerformance(BigDecimal performance) {
        this.performance = performance;
    }
}
