package csci812_project.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public class LoanPaymentDTO {

    /** Unique payment ID */
    private Long id;

    /** Loan ID this payment is associated with */
    private Long loanId;

    /** User ID making the payment */
    private Long userId;

    /** Amount paid */
    private BigDecimal paymentAmount;

    /** Payment date */
    private LocalDateTime paymentDate;

    /** Remaining balance after this payment */
    private BigDecimal remainingBalance;

    // Constructor
    public LoanPaymentDTO(){}
    public LoanPaymentDTO(Long id, Long loanId, Long userId, BigDecimal paymentAmount, LocalDateTime paymentDate,
                          BigDecimal remainingBalance) {
        this.id = id;
        this.loanId = loanId;
        this.userId = userId;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
        this.remainingBalance = remainingBalance;
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(BigDecimal remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
}

