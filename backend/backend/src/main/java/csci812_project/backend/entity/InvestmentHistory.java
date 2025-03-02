package csci812_project.backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "investment_history")
public class InvestmentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "investment_id", nullable = false, foreignKey = @ForeignKey(name = "fk_investment_history_investment"))
    private Investment investment;

    @Column(name = "current_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal currentValue;

    @Column(name = "performance", precision = 5, scale = 2)
    private BigDecimal performance;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    @Column(name = "returns_generated", precision = 15, scale = 2)
    private BigDecimal returnsGenerated;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_investment_user"))
//    private User user;


    // ✅ Automatically set recordedAt before persisting
    @PrePersist
    protected void onCreate() {
        this.recordedAt = LocalDateTime.now();
    }

    // ✅ Constructor for easy creation

    public InvestmentHistory(){}

    public InvestmentHistory(Long historyId, Investment investment, BigDecimal currentValue, BigDecimal performance,
                             LocalDateTime recordedAt, BigDecimal returnsGenerated) {
        this.historyId = historyId;
        this.investment = investment;
        this.currentValue = currentValue;
        this.performance = performance;
        this.recordedAt = recordedAt;
        this.returnsGenerated = returnsGenerated;
    }

    // ✅ Getters and Setters
    public Long getHistoryId() { return historyId; }
    public void setHistoryId(Long historyId) { this.historyId = historyId; }

    public Investment getInvestment() { return investment; }
    public void setInvestment(Investment investment) { this.investment = investment; }

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
