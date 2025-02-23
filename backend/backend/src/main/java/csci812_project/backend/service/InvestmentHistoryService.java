package csci812_project.backend.service;

import csci812_project.backend.dto.InvestmentHistoryDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface InvestmentHistoryService {

    void recordInvestmentHistory(Long investmentId);

    List<InvestmentHistoryDTO> getInvestmentHistory(Long investmentId);

    List<InvestmentHistoryDTO> getInvestmentHistoryByDateRange(Long investmentId,
                                                               LocalDateTime startDate,
                                                               LocalDateTime endDate);
}
