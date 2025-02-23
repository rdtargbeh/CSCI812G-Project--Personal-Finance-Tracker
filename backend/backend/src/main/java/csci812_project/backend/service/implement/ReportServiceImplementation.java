package csci812_project.backend.service.implement;

import csci812_project.backend.dto.ReportDTO;
import csci812_project.backend.entity.Report;
import csci812_project.backend.entity.SavingsGoal;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.ReportFileFormat;
import csci812_project.backend.enums.ReportGeneratedBy;
import csci812_project.backend.mapper.ReportMapper;
import csci812_project.backend.repository.ReportRepository;
import csci812_project.backend.repository.SavingsGoalRepository;
import csci812_project.backend.repository.TransactionRepository;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

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


    @Override
    @Transactional
    public ReportDTO generateReport(Long userId, LocalDate startDate, LocalDate endDate, ReportFileFormat format) {
        BigDecimal totalIncome = transactionRepository.calculateTotalIncome(userId, startDate, endDate);
        BigDecimal totalExpense = transactionRepository.calculateTotalExpense(userId, startDate, endDate);

        Report report = new Report();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        report.setUser(user); // ✅ Fetch user from the database

        report.setStartDate(startDate);
        report.setEndDate(endDate);
        report.setTotalIncome(totalIncome);
        report.setTotalExpense(totalExpense);
        report.calculateNetBalance(); // ✅ Automatically calculates net balance
        report.setFileFormat(format);
        report.setGeneratedBy(ReportGeneratedBy.SYSTEM);

        reportRepository.save(report);

        return reportMapper.toDTO(report);
    }


    @Override
    public List<ReportDTO> getReportsByUser(Long userId) {
        return reportRepository.findByUser_UserId(userId)
                .stream()
                .map(reportMapper::toDTO)
                .toList();
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

    /**
     * ✅ Generate a report for a specific user within a given date range.
     */
    private void generateReportForUser(Long userId, LocalDate startDate, LocalDate endDate) {
        BigDecimal totalIncome = transactionRepository.calculateTotalIncome(userId, startDate, endDate);
        BigDecimal totalExpense = transactionRepository.calculateTotalExpense(userId, startDate, endDate);

        Report report = new Report();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        report.setUser(user); // ✅ Fetch user from the database

        report.setStartDate(startDate);
        report.setEndDate(endDate);
        report.setTotalIncome(totalIncome);
        report.setTotalExpense(totalExpense);
        report.calculateNetBalance(); // ✅ Auto-update balance
        report.setFileFormat(ReportFileFormat.PDF);
        report.setGeneratedBy(ReportGeneratedBy.SYSTEM);

        reportRepository.save(report);
        System.out.println("📊 Report generated for User ID: " + userId);
    }

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
