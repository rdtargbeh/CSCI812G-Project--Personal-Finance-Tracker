package csci812_project.backend.service;

import csci812_project.backend.dto.ReportDTO;
import csci812_project.backend.enums.ReportFileFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    ReportDTO generateReport(Long userId, LocalDate startDate, LocalDate endDate, ReportFileFormat format);

    List<ReportDTO> getReportsByUser(Long userId);

    void generateMonthlyReports();
}

