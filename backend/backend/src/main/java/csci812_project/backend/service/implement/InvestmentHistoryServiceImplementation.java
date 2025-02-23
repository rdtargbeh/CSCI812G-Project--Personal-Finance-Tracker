package csci812_project.backend.service.implement;

import csci812_project.backend.dto.InvestmentHistoryDTO;
import csci812_project.backend.entity.Investment;
import csci812_project.backend.entity.InvestmentHistory;
import csci812_project.backend.mapper.InvestmentHistoryMapper;
import csci812_project.backend.repository.InvestmentHistoryRepository;
import csci812_project.backend.repository.InvestmentRepository;
import csci812_project.backend.service.InvestmentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvestmentHistoryServiceImplementation implements InvestmentHistoryService {

    @Autowired
    private InvestmentRepository investmentRepository;
    @Autowired
    private InvestmentHistoryRepository investmentHistoryRepository;
    @Autowired
    private InvestmentHistoryMapper investmentHistoryMapper;


    @Override
    @Transactional
    public void recordInvestmentHistory(Long investmentId) {
        Investment investment = investmentRepository.findById(investmentId)
                .orElseThrow(() -> new RuntimeException("Investment not found"));

        BigDecimal performance = investment.getCurrentValue()
                .subtract(investment.getAmountInvested())
                .divide(investment.getAmountInvested(), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        // ✅ Use DTO format first
        InvestmentHistoryDTO dto = new InvestmentHistoryDTO();
        dto.setInvestmentId(investment.getInvestmentId());
        dto.setCurrentValue(investment.getCurrentValue());
        dto.setPerformance(performance);
        dto.setRecordedAt(LocalDateTime.now());

        // ✅ Convert DTO to Entity using your mapper
        InvestmentHistory history = investmentHistoryMapper.toEntity(dto, investment);

        investmentHistoryRepository.save(history);
    }


    @Override
    public List<InvestmentHistoryDTO> getInvestmentHistory(Long investmentId) {
        List<InvestmentHistory> history = investmentHistoryRepository.findByInvestment_InvestmentId(investmentId);
        return history.stream().map(investmentHistoryMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves investment history for a specific investment within a date range.
     */
    @Transactional(readOnly = true)
    public List<InvestmentHistoryDTO> getInvestmentHistoryByDateRange(Long investmentId,
                                                                      LocalDateTime startDate,
                                                                      LocalDateTime endDate) {
        List<InvestmentHistory> historyRecords = investmentHistoryRepository.findByInvestmentAndDateRange(
                investmentId, startDate, endDate);

        return historyRecords.stream()
                .map(investmentHistoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
