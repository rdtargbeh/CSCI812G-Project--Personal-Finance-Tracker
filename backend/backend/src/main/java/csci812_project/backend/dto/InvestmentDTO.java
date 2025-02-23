package csci812_project.backend.dto;

import csci812_project.backend.enums.InvestmentType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestmentDTO {

    /** Unique investment ID */
    private Long id;

    /** Type of investment (STOCKS, CRYPTO, MUTUAL_FUNDS, REAL_ESTATE) */
    private InvestmentType investmentType;

    /** Name of the investment asset (e.g., "Apple Stocks", "Bitcoin") */
    private String assetName;

    /** Initial amount invested */
    private BigDecimal amountInvested;

    /** Current market value of the investment */
    private BigDecimal currentValue;

    /** Date of purchase */
    private LocalDateTime purchaseDate;

    /** Last updated timestamp */
    private LocalDateTime lastUpdated;

    /** Timestamp for when the investment record was created */
    private LocalDateTime dateCreated;
}
