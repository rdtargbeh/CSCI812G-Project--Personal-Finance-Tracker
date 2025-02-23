package csci812_project.backend.controller;

import csci812_project.backend.dto.ReportDTO;
import csci812_project.backend.enums.ReportFileFormat;
import csci812_project.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ReportDTO> generateReport(
            @RequestParam Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(defaultValue = "PDF") ReportFileFormat format) {

        ReportDTO report = reportService.generateReport(
                userId, LocalDate.parse(startDate), LocalDate.parse(endDate), format);

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
}

