package csci812_project.backend.controller;

import csci812_project.backend.dto.ReportDTO;
import csci812_project.backend.dto.ReportRequestDTO;
import csci812_project.backend.enums.ReportFileFormat;
import csci812_project.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private  ReportService reportService;


    /**
     * ✅ Generate a financial report for a user.
     */
    @PostMapping("/generate")
    public ResponseEntity<ReportDTO> generateReport(@RequestBody ReportRequestDTO requestDTO) {
        ReportDTO report = reportService.generateReport(
                requestDTO.getUserId(),
                requestDTO.getStartDate(),
                requestDTO.getEndDate(),
                requestDTO.getFileFormat());

        return ResponseEntity.ok(report);
    }

    /**
     * ✅ Retrieve all reports for a user.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<ReportDTO>> getUserReports(@PathVariable Long userId) {
        return ResponseEntity.ok(reportService.getReportsByUser(userId));
    }

    /**
     * ✅ Manually triggers the monthly report generation for all users.
     * Only Admins should have access to this.
     */
    @PostMapping("/generate-monthly")
    public ResponseEntity<String> generateMonthlyReports() {
        reportService.generateMonthlyReports();
        return ResponseEntity.status(HttpStatus.OK).body("✅ Monthly reports generated successfully!");
    }

    /** ✅ Generate Loan Report */
    @GetMapping("/user/{userId}/loans")
    public ResponseEntity<ReportDTO> generateLoanReport(@PathVariable Long userId) {
        return ResponseEntity.ok(reportService.generateLoanReport(userId, LocalDate.now().minusMonths(1), LocalDate.now()));
    }

    /** ✅ Generate Investment Report */
    @GetMapping("/user/{userId}/investments")
    public ResponseEntity<ReportDTO> generateInvestmentReport(@PathVariable Long userId) {
        return ResponseEntity.ok(reportService.generateInvestmentReport(userId, LocalDate.now().minusMonths(1), LocalDate.now()));
    }

    /** ✅ Generate Savings Goal Report */
    @GetMapping("/savings-goal")
    public ResponseEntity<ReportDTO> generateSavingsGoalReport(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        ReportDTO report = reportService.generateSavingsGoalReport(userId, startDate, endDate);
        return ResponseEntity.ok(report);
    }
}

