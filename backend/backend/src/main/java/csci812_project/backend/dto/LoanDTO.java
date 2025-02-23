package csci812_project.backend.dto;

import csci812_project.backend.enums.LoanStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}

