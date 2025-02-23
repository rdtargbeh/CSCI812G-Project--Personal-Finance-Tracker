package csci812_project.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}

