package csci812_project.backend.service.implement;

import csci812_project.backend.dto.ReportDTO;
import csci812_project.backend.entity.*;
import csci812_project.backend.enums.ReportFileFormat;
import csci812_project.backend.enums.ReportGeneratedBy;
import csci812_project.backend.mapper.ReportMapper;
import csci812_project.backend.repository.*;
import csci812_project.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ReportServiceImplementation implements ReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SavingsGoalRepository savingsGoalRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private InvestmentRepository investmentRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LoanPaymentRepository loanPaymentRepository;
    @Autowired
    private InvestmentHistoryRepository investmentHistoryRepository;



    @Override
    @Transactional
    public ReportDTO generateReport(Long userId, LocalDate startDate, LocalDate endDate, ReportFileFormat format) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Convert LocalDate to LocalDateTime for accurate filtering
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        // ✅ Fetch Financial Data (Handle `null` values manually)
        BigDecimal totalIncome = Objects.requireNonNullElse(transactionRepository.calculateTotalIncome(userId, startDateTime, endDateTime), BigDecimal.ZERO);
        BigDecimal totalExpense = Objects.requireNonNullElse(transactionRepository.calculateTotalExpense(userId, startDateTime, endDateTime), BigDecimal.ZERO);
        BigDecimal totalSavings = Objects.requireNonNullElse(savingsGoalRepository.getTotalSavingsByUserId(userId), BigDecimal.ZERO);
        BigDecimal totalLoans = Objects.requireNonNullElse(loanRepository.getTotalLoanAmountByUserId(userId), BigDecimal.ZERO);
        BigDecimal outstandingDebt = Objects.requireNonNullElse(loanRepository.getOutstandingLoanBalanceByUserId(userId), BigDecimal.ZERO);
        BigDecimal totalInvestments = Objects.requireNonNullElse(investmentRepository.getTotalInvestmentsByUserId(userId), BigDecimal.ZERO);
        BigDecimal totalInvestmentReturns = Objects.requireNonNullElse(investmentHistoryRepository.getTotalInvestmentReturnsByUserId(userId, startDateTime, endDateTime), BigDecimal.ZERO);
        BigDecimal totalBalance = Objects.requireNonNullElse(accountRepository.getTotalAccountBalanceByUserId(userId), BigDecimal.ZERO);

        // ✅ Compute Net Worth: (Account Balance + Investments + Savings - Expenses - Loans)
        BigDecimal netWorth = totalBalance
                .add(totalInvestments)
                .add(totalSavings)
                .subtract(totalExpense)
                .subtract(outstandingDebt);

        // ✅ Create and Save Report
        Report report = new Report();
        report.setUser(user);
        report.setStartDate(startDate);
        report.setEndDate(endDate);
        report.setTotalIncome(totalIncome);
        report.setTotalExpense(totalExpense);
        report.setNetBalance(netWorth); // Net Worth Calculation
        report.setFileFormat(format);
        report.setGeneratedBy(ReportGeneratedBy.SYSTEM);

        reportRepository.save(report);
        return reportMapper.toDTO(report);
    }


    @Override
    public ReportDTO generateLoanReport(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Loan> loans = loanRepository.findByUser_UserId(userId);
        BigDecimal totalLoanAmount = loans.stream().map(Loan::getAmountBorrowed).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal outstandingDebt = loans.stream().map(Loan::getOutstandingBalance).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalPaid = loanPaymentRepository.getTotalLoanPaymentsByUserId(userId, startDate, endDate);

        Report report = new Report();
        report.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
        report.setStartDate(startDate);
        report.setEndDate(endDate);
        report.setTotalIncome(totalPaid);
        report.setTotalExpense(BigDecimal.ZERO); // Loans are not expenses, they're debts
        report.setNetBalance(outstandingDebt.negate()); // Show as a negative balance
        report.setFileFormat(ReportFileFormat.PDF);
        report.setGeneratedBy(ReportGeneratedBy.SYSTEM);

        reportRepository.save(report);
        return reportMapper.toDTO(report);
    }


    @Override
    @Transactional
    public ReportDTO generateInvestmentReport(Long userId, LocalDate startDate, LocalDate endDate) {
        // ✅ Convert `LocalDate` to `LocalDateTime`
        LocalDateTime startDateTime = startDate.atStartOfDay(); // Start of the day
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59); // End of the day

        // ✅ Fetch investment data
        List<Investment> investments = investmentRepository.findByUser_UserId(userId);
        BigDecimal totalInvested = investments.stream()
                .map(Investment::getInvestedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalReturns = Objects.requireNonNullElse(
                investmentHistoryRepository.getTotalInvestmentReturnsByUserId(userId, startDateTime, endDateTime),
                BigDecimal.ZERO
        );

        // ✅ Create and Save Report
        Report report = new Report();
        report.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));
        report.setStartDate(startDate);
        report.setEndDate(endDate);
        report.setTotalIncome(totalReturns);
        report.setTotalExpense(BigDecimal.ZERO); // No direct expenses
        report.setNetBalance(totalInvested.add(totalReturns)); // Investment growth
        report.setFileFormat(ReportFileFormat.PDF);
        report.setGeneratedBy(ReportGeneratedBy.SYSTEM);

        reportRepository.save(report);
        return reportMapper.toDTO(report);
    }


    /**
     * ✅ Runs automatically on the 1st day of every month at midnight.
     * Generates reports for all users for the previous month.
     */
    @Scheduled(cron = "0 0 0 1 * ?") // Runs at 12:00 AM on the 1st of every month
    @Transactional
    @Override
    public void generateMonthlyReports() {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusMonths(1).withDayOfMonth(1);
        LocalDate endDate = today.minusMonths(1).withDayOfMonth(today.minusMonths(1).lengthOfMonth());

        List<User> users = userRepository.findAll();

        for (User user : users) {
            generateReportForUser(user.getUserId(), startDate, endDate);
        }

        System.out.println("✅ Monthly financial reports generated for all users!");
    }

    @Override
    public List<ReportDTO> getReportsByUser(Long userId) {
        return reportRepository.findByUser_UserId(userId)
                .stream()
                .map(reportMapper::toDTO)
                .toList();
    }

    /**
     * ✅ Generate a report for a specific user within a given date range.
     */
    private void generateReportForUser(Long userId, LocalDate startDate, LocalDate endDate) {
        // ✅ Convert `LocalDate` to `LocalDateTime`
        LocalDateTime startDateTime = startDate.atStartOfDay(); // Start of the day
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59); // End of the day

        // ✅ Fetch financial data with correct date types
        BigDecimal totalIncome = transactionRepository.calculateTotalIncome(userId, startDateTime, endDateTime);
        BigDecimal totalExpense = transactionRepository.calculateTotalExpense(userId, startDateTime, endDateTime);

        // ✅ Create Report
        Report report = new Report();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        report.setUser(user); // ✅ Fetch user from the database

        report.setStartDate(startDate);  // Keep LocalDate in the report entity
        report.setEndDate(endDate);
        report.setTotalIncome(totalIncome);
        report.setTotalExpense(totalExpense);
        report.calculateNetBalance(); // ✅ Auto-update balance
        report.setFileFormat(ReportFileFormat.PDF);
        report.setGeneratedBy(ReportGeneratedBy.SYSTEM);

        // ✅ Save Report
        reportRepository.save(report);
        System.out.println("📊 Report generated for User ID: " + userId);
    }

    @Override
    public ReportDTO generateSavingsGoalReport(Long userId, LocalDate startDate, LocalDate endDate) {
        List<SavingsGoal> goals = savingsGoalRepository.findByUser_UserId(userId);
        BigDecimal totalSaved = goals.stream().map(SavingsGoal::getCurrentAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalTarget = goals.stream().map(SavingsGoal::getTargetAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal progress = totalSaved.divide(totalTarget, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        Report report = new Report();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        report.setUser(user); // ✅ Fetch user from the database

        report.setStartDate(startDate);
        report.setEndDate(endDate);
        report.setTotalIncome(totalSaved);
        report.setTotalExpense(BigDecimal.ZERO); // No expenses in savings
        report.calculateNetBalance();
        report.setFileFormat(ReportFileFormat.PDF);
        report.setGeneratedBy(ReportGeneratedBy.SYSTEM);

        reportRepository.save(report);
        return reportMapper.toDTO(report);
    }

}
