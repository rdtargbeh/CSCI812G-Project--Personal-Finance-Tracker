package csci812_project.backend.dto;

import csci812_project.backend.enums.ReportFileFormat;

import javax.xml.crypto.Data;
import java.time.LocalDate;

public class ReportRequestDTO {
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReportFileFormat fileFormat;


    public ReportRequestDTO(){}
    public ReportRequestDTO(Long userId, LocalDate startDate, LocalDate endDate, ReportFileFormat fileFormat) {
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fileFormat = fileFormat;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ReportFileFormat getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(ReportFileFormat fileFormat) {
        this.fileFormat = fileFormat;
    }
}
