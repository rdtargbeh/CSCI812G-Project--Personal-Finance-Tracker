package csci812_project.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//   Investment ReportDTO to pass on investment attributes in investment reported data in report service
public class InvestmentReportDTO {
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalInvested;
    private BigDecimal totalCurrentValue;
    private BigDecimal totalReturns;
    private List<InvestmentDetailsDTO> investments;


    // Constructor
    public InvestmentReportDTO(){}
    public InvestmentReportDTO(Long userId, LocalDate startDate, LocalDate endDate, BigDecimal totalInvested,
                               BigDecimal totalCurrentValue, BigDecimal totalReturns, List<InvestmentDetailsDTO> investments) {
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalInvested = totalInvested;
        this.totalCurrentValue = totalCurrentValue;
        this.totalReturns = totalReturns;
        this.investments = investments;
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

    public BigDecimal getTotalInvested() {
        return totalInvested;
    }

    public void setTotalInvested(BigDecimal totalInvested) {
        this.totalInvested = totalInvested;
    }

    public BigDecimal getTotalCurrentValue() {
        return totalCurrentValue;
    }

    public void setTotalCurrentValue(BigDecimal totalCurrentValue) {
        this.totalCurrentValue = totalCurrentValue;
    }

    public BigDecimal getTotalReturns() {
        return totalReturns;
    }

    public void setTotalReturns(BigDecimal totalReturns) {
        this.totalReturns = totalReturns;
    }

    public List<InvestmentDetailsDTO> getInvestments() {
        return investments;
    }

    public void setInvestments(List<InvestmentDetailsDTO> investments) {
        this.investments = investments;
    }
}
