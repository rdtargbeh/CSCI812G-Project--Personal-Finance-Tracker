package csci812_project.backend.dto;

import csci812_project.backend.enums.ReportGeneratedBy;
import csci812_project.backend.enums.ReportFileFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDTO {

    /** Unique report ID */
    private Long id;

    /** Start and end dates for the report */
    private LocalDate startDate;
    private LocalDate endDate;

    /** Financial summary */
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netBalance;

    /** Report metadata */
    private ReportGeneratedBy generatedBy;
    private ReportFileFormat fileFormat;
    private LocalDate dateCreated;
}

