package csci812_project.backend.dto;

import csci812_project.backend.enums.LoanStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public class LoanDTO {

    /** Unique loan ID */
    private Long id;

    /** Lender name (Bank, Credit Card Company, etc.) */
    private String lenderName;

    /** Amount borrowed (Principal loan amount) */
    private BigDecimal amountBorrowed;

    /** Outstanding balance */
    private BigDecimal outstandingBalance;

    /** Interest rate (Annual) */
    private BigDecimal interestRate;

    /** Total interest paid */
    private BigDecimal interestPaid;

    /** Monthly installment amount */
    private BigDecimal monthlyPayment;

    /** Total amount paid (Principal + Interest) */
    private BigDecimal totalAmountPaid;

    /** Loan term in years */
    private int numberYears;

    /** Next due date */
    private LocalDate dueDate;

    /** Loan status (ACTIVE, PAID_OFF, DEFAULTED) */
    private LoanStatus status;

    /** Timestamp for when the loan record was created */
    private LocalDateTime dateCreated;

    // Constructor
    public LoanDTO(){}
    public LoanDTO(Long id, String lenderName, BigDecimal amountBorrowed, BigDecimal outstandingBalance, BigDecimal interestRate,
                   BigDecimal interestPaid, BigDecimal monthlyPayment, BigDecimal totalAmountPaid, int numberYears, LocalDate dueDate,
                   LoanStatus status, LocalDateTime dateCreated) {
        this.id = id;
        this.lenderName = lenderName;
        this.amountBorrowed = amountBorrowed;
        this.outstandingBalance = outstandingBalance;
        this.interestRate = interestRate;
        this.interestPaid = interestPaid;
        this.monthlyPayment = monthlyPayment;
        this.totalAmountPaid = totalAmountPaid;
        this.numberYears = numberYears;
        this.dueDate = dueDate;
        this.status = status;
        this.dateCreated = dateCreated;
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLenderName() {
        return lenderName;
    }

    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
    }

    public BigDecimal getAmountBorrowed() {
        return amountBorrowed;
    }

    public void setAmountBorrowed(BigDecimal amountBorrowed) {
        this.amountBorrowed = amountBorrowed;
    }

    public BigDecimal getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(BigDecimal outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(BigDecimal interestPaid) {
        this.interestPaid = interestPaid;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public BigDecimal getTotalAmountPaid() {
        return totalAmountPaid;
    }

    public void setTotalAmountPaid(BigDecimal totalAmountPaid) {
        this.totalAmountPaid = totalAmountPaid;
    }

    public int getNumberYears() {
        return numberYears;
    }

    public void setNumberYears(int numberYears) {
        this.numberYears = numberYears;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}

