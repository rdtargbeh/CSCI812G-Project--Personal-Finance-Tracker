package csci812_project.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetDTO {

    /** Unique budget ID */
    private Long budgetId;

    /** User ID associated with the budget */
    private Long userId;

    /** Category ID associated with the budget */
    private Long categoryId;

    /** Maximum spending amount allowed within the budget period */
    private BigDecimal amountLimit;

    /** Start date of the budget period */
    private LocalDate startDate;

    /** End date of the budget period */
    private LocalDate endDate;

    /** Budget type (STRICT or FLEXIBLE) */
    private String budgetType;


    /** Amount that rolls over to the next period (if applicable) */
    private BigDecimal rolloverAmount;

    /** Indicates whether the budget is deleted (soft delete) */
    private boolean isDeleted;

    /** Timestamp for when the budget was created */
    private LocalDateTime dateCreated;

    /** Timestamp for when the budget was last updated */
    private LocalDateTime dateUpdated;
}
