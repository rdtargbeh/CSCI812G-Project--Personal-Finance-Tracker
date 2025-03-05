package csci812_project.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


//  Transaction ReportDTO to pass on Transaction attributes in Transaction reported data in report service

public class TransactionReportDTO {
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private List<TransactionDetailsDTO> transactions;

    // Constructor
    public TransactionReportDTO(){}
    public TransactionReportDTO(Long userId, LocalDate startDate, LocalDate endDate, BigDecimal totalIncome,
                                BigDecimal totalExpense, List<TransactionDetailsDTO> transactions) {
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.transactions = transactions;
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

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }

    public List<TransactionDetailsDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDetailsDTO> transactions) {
        this.transactions = transactions;
    }
}

