package csci812_project.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//   Saving goal ReportDTO to pass on Saving goal attributes in Saving goal reported data in report service
public class SavingsGoalReportDTO {
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalSaved;
    private BigDecimal totalTarget;
    private BigDecimal progress;
    private List<SavingsGoalDetailsDTO> savingsGoals;

    // Constructor
    public SavingsGoalReportDTO(){}
    public SavingsGoalReportDTO(Long userId, LocalDate startDate, LocalDate endDate, BigDecimal totalSaved, BigDecimal totalTarget,
                                BigDecimal progress, List<SavingsGoalDetailsDTO> savingsGoals) {
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalSaved = totalSaved;
        this.totalTarget = totalTarget;
        this.progress = progress;
        this.savingsGoals = savingsGoals;
    }

    // Getter and Setter

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotalSaved() {
        return totalSaved;
    }

    public void setTotalSaved(BigDecimal totalSaved) {
        this.totalSaved = totalSaved;
    }

    public BigDecimal getTotalTarget() {
        return totalTarget;
    }

    public void setTotalTarget(BigDecimal totalTarget) {
        this.totalTarget = totalTarget;
    }

    public BigDecimal getProgress() {
        return progress;
    }

    public void setProgress(BigDecimal progress) {
        this.progress = progress;
    }

    public List<SavingsGoalDetailsDTO> getSavingsGoals() {
        return savingsGoals;
    }

    public void setSavingsGoals(List<SavingsGoalDetailsDTO> savingsGoals) {
        this.savingsGoals = savingsGoals;
    }
}
