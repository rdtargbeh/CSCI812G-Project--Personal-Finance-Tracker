package csci812_project.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_payments")
public class LoanPayment {

    /**
     * Unique payment ID (Primary Key).
     * Auto-generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    /**
     * Foreign Key linking the payment to a loan.
     * Ensures that each payment belongs to a specific loan.
     */
    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false, foreignKey = @ForeignKey(name = "fk_loan_payment_loan"))
    private Loan loan;

    /**
     * Foreign Key linking the payment to a user.
     * Ensures that each payment is associated with a specific user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_loan_payment_user"))
    private User user;

    /**
     * Amount paid in this transaction.
     * Cannot be negative.
     */
    @Column(name = "payment_amount", precision = 15, scale = 2)
    @DecimalMin(value = "0.00", message = "Payment amount cannot be negative")
    private BigDecimal paymentAmount = BigDecimal.ZERO; // ✅ Default to zero

    @Column(name = "extra_payment", precision = 15, scale = 2)
    private BigDecimal extraPayment = BigDecimal.ZERO;

    @Column(name = "principal_paid", precision = 15, scale = 2)
    private BigDecimal principalPaid = BigDecimal.ZERO; // ✅ Track principal amount paid

    /**
     * Total interest paid over the loan lifetime.
     * Cannot be negative.
     */
    @Column(name = "interest_paid", precision = 15, scale = 2)
    @DecimalMin(value = "0.00", message = "Interest paid cannot be negative")
    private BigDecimal interestPaid = BigDecimal.ZERO;

    /**
     * Total amount repaid (principal + interest).
     * Cannot be negative.
     */
    @Column(name = "total_amount_paid", precision = 15, scale = 2)
    @DecimalMin(value = "0.00", message = "Total amount paid cannot be negative")
    private BigDecimal totalAmountPaid = BigDecimal.ZERO;

    /**
     * Remaining balance on the loan after this payment.
     * Cannot be negative.
     */
    @Column(name = "remaining_balance", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.00", message = "Remaining balance cannot be negative")
    private BigDecimal remainingBalance;

    @Column(name = "last_payment_date")
    private LocalDate lastPaymentDate; // ✅ Track last payment date

    @Column(name = "next_due_date", nullable = false)
    private LocalDate nextDueDate; // ✅ Track next due dat

    /**
     * Date and time of the payment.
     * Defaults to the current timestamp.
     */
    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate = LocalDateTime.now();


    // ✅ Auto-update next due date after every payment
    public void updateNextDueDate() {
        this.lastPaymentDate = LocalDate.now();
        this.nextDueDate = this.lastPaymentDate.plusMonths(1);
    }


    /**
     * ✅ Applies a single **scheduled** monthly payment (AutoPay).
     */
    public void applyPayment(BigDecimal paymentAmount, BigDecimal extraPayment) {
        if ((paymentAmount == null || paymentAmount.compareTo(BigDecimal.ZERO) <= 0) &&
                (extraPayment == null || extraPayment.compareTo(BigDecimal.ZERO) <= 0)) {
            throw new RuntimeException("Payment amount must be greater than zero!");
        }

        if (paymentAmount != null && extraPayment != null) {
            throw new RuntimeException("You can only make either a monthly payment OR an extra payment at a time, not both.");
        }

        BigDecimal monthlyInterestRate = loan.getInterestRate().divide(BigDecimal.valueOf(100 * 12), RoundingMode.HALF_UP);
        BigDecimal interestForMonth = loan.getOutstandingBalance().multiply(monthlyInterestRate).setScale(2, RoundingMode.HALF_UP);

        BigDecimal principalPortion = paymentAmount != null ? paymentAmount.subtract(interestForMonth) : extraPayment;

        this.remainingBalance = loan.getOutstandingBalance().subtract(principalPortion);
        this.principalPaid = this.principalPaid.add(principalPortion);
        this.interestPaid = this.interestPaid.add(interestForMonth);
        this.totalAmountPaid = this.totalAmountPaid.add(principalPortion.add(interestForMonth));

        loan.setOutstandingBalance(this.remainingBalance);
        loan.updateLoanStatus();
    }





    // Constructor
    public LoanPayment(){}

//    public void initializePayment(Loan loan, User user, BigDecimal paymentAmount) {
//        this.loan = loan;
//        this.user = user;
//        this.paymentAmount = paymentAmount;
//        this.paymentDate = LocalDateTime.now();
//        this.remainingBalance = loan.getOutstandingBalance().subtract(paymentAmount);
//        this.lastPaymentDate = LocalDate.now();
//        this.nextDueDate = this.lastPaymentDate.plusMonths(1); // Next due date 1 month later
//    }

    public LoanPayment(Long paymentId, Loan loan, User user, BigDecimal paymentAmount, BigDecimal extraPayment,
                       BigDecimal principalPaid, BigDecimal interestPaid, BigDecimal totalAmountPaid,
                       BigDecimal remainingBalance, LocalDate lastPaymentDate, LocalDate nextDueDate, LocalDateTime paymentDate) {
        this.paymentId = paymentId;
        this.loan = loan;
        this.user = user;
        this.paymentAmount = paymentAmount;
        this.extraPayment = extraPayment;
        this.principalPaid = principalPaid;
        this.interestPaid = interestPaid;
        this.totalAmountPaid = totalAmountPaid;
        this.remainingBalance = remainingBalance;
        this.lastPaymentDate = lastPaymentDate;
        this.nextDueDate = nextDueDate;
        this.paymentDate = paymentDate;
    }

// Getter and Setter

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public @DecimalMin(value = "0.00", message = "Payment amount cannot be negative") BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(@DecimalMin(value = "0.00", message = "Payment amount cannot be negative") BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public BigDecimal getExtraPayment() {
        return extraPayment;
    }

    public void setExtraPayment(BigDecimal extraPayment) {
        this.extraPayment = extraPayment;
    }

    public BigDecimal getPrincipalPaid() {
        return principalPaid;
    }

    public void setPrincipalPaid(BigDecimal principalPaid) {
        this.principalPaid = principalPaid;
    }

    public @DecimalMin(value = "0.00", message = "Interest paid cannot be negative") BigDecimal getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(@DecimalMin(value = "0.00", message = "Interest paid cannot be negative") BigDecimal interestPaid) {
        this.interestPaid = interestPaid;
    }

    public @DecimalMin(value = "0.00", message = "Total amount paid cannot be negative") BigDecimal getTotalAmountPaid() {
        return totalAmountPaid;
    }

    public void setTotalAmountPaid(@DecimalMin(value = "0.00", message = "Total amount paid cannot be negative") BigDecimal totalAmountPaid) {
        this.totalAmountPaid = totalAmountPaid;
    }

    public @DecimalMin(value = "0.00", message = "Remaining balance cannot be negative") BigDecimal getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(@DecimalMin(value = "0.00", message = "Remaining balance cannot be negative") BigDecimal remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public LocalDate getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(LocalDate lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public LocalDate getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(LocalDate nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}
