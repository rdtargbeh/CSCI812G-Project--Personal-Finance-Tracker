package csci812_project.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvestmentHistoryDTO {
    private Long historyId;
    private Long investmentId;
    private BigDecimal currentValue;
    private BigDecimal performance;
    private LocalDateTime recordedAt;
    private BigDecimal returnsGenerated;


    // Constructor

    public InvestmentHistoryDTO(){}

    public InvestmentHistoryDTO(Long historyId, Long investmentId, BigDecimal currentValue, BigDecimal performance,
                                LocalDateTime recordedAt, BigDecimal returnsGenerated) {
        this.historyId = historyId;
        this.investmentId = investmentId;
        this.currentValue = currentValue;
        this.performance = performance;
        this.recordedAt = recordedAt;
        this.returnsGenerated = returnsGenerated;
    }

    // ✅ Getters and Setters
    public Long getHistoryId() { return historyId; }
    public void setHistoryId(Long historyId) { this.historyId = historyId; }

    public Long getInvestmentId() { return investmentId; }
    public void setInvestmentId(Long investmentId) { this.investmentId = investmentId; }

    public BigDecimal getCurrentValue() { return currentValue; }
    public void setCurrentValue(BigDecimal currentValue) { this.currentValue = currentValue; }

    public BigDecimal getPerformance() { return performance; }
    public void setPerformance(BigDecimal performance) { this.performance = performance; }

    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }

    public BigDecimal getReturnsGenerated() {
        return returnsGenerated;
    }

    public void setReturnsGenerated(BigDecimal returnsGenerated) {
        this.returnsGenerated = returnsGenerated;
    }
}

