package csci812_project.backend.service;

import csci812_project.backend.dto.*;
import csci812_project.backend.enums.ReportFileFormat;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    ReportDTO generateReport(Long userId, LocalDate startDate, LocalDate endDate, ReportFileFormat format);

    List<ReportDTO> getReportsByUser(Long userId);

    void generateMonthlyReports();

    LoanReportDTO generateLoanReport(Long userId, LocalDate startDate, LocalDate endDate);

    InvestmentReportDTO generateInvestmentReport(Long userId, LocalDate startDate, LocalDate endDate);

    SavingsGoalReportDTO generateSavingsGoalReport(Long userId, LocalDate startDate, LocalDate endDate);

    List<ReportDTO> getReportsByUserAndDate(Long userId, LocalDate startDate, LocalDate endDate);

    BudgetReportDTO generateBudgetReport(Long userId, LocalDate startDate, LocalDate endDate);

    TransactionReportDTO generateTransactionReport(Long userId, LocalDate startDate, LocalDate endDate);


}

