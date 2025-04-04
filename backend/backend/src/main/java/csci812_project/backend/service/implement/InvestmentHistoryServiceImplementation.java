package csci812_project.backend.service.implement;

import csci812_project.backend.dto.InvestmentHistoryDTO;
import csci812_project.backend.entity.Investment;
import csci812_project.backend.entity.InvestmentHistory;
import csci812_project.backend.exception.NotFoundException;
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
    public void recordInvestmentHistory(Long investmentId, BigDecimal newCurrentValue) {
        Investment investment = investmentRepository.findById(investmentId)
                .orElseThrow(() -> new NotFoundException("Investment not found"));

        BigDecimal amountInvested = investment.getAmountInvested();

        // ✅ Calculate `returnsGenerated`
        BigDecimal returnsGenerated = newCurrentValue.subtract(amountInvested);

        // ✅ Avoid division by zero when calculating performance
        BigDecimal performance = BigDecimal.ZERO;
        if (amountInvested.compareTo(BigDecimal.ZERO) > 0) {
            performance = returnsGenerated
                    .divide(amountInvested, 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        // ✅ Update `Investment` entity with new `currentValue` and `performance`
        investment.setCurrentValue(newCurrentValue);
        investment.setPerformance(performance);
        investment.setLastUpdated(LocalDateTime.now());
        investmentRepository.save(investment);  // 🔹 Save the updated investment

        // ✅ Save Investment History Record
        InvestmentHistory history = new InvestmentHistory();
        history.setInvestment(investment);
        history.setCurrentValue(newCurrentValue);
        history.setPerformance(performance);
        history.setReturnsGenerated(returnsGenerated);
        history.setRecordedAt(LocalDateTime.now());

        investmentHistoryRepository.save(history);
    }


    @Override
    public List<InvestmentHistoryDTO> getInvestmentHistory(Long investmentId) {
        List<InvestmentHistory> history = investmentHistoryRepository.findByInvestment_InvestmentId(investmentId);

        if (history.isEmpty()) {
            throw new NotFoundException("No investment history found for investment ID: " + investmentId);
        }
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

//    @Override
//    @Transactional
//    public void recordInvestmentsHistory(Long investmentId) {
//        Investment investment = investmentRepository.findById(investmentId)
//                .orElseThrow(() -> new NotFoundException("Investment not found"));
//
//        BigDecimal amountInvested = investment.getAmountInvested();
//        BigDecimal currentValue = investment.getCurrentValue();
//
//        // ✅ Calculate `returnsGenerated`
//        BigDecimal returnsGenerated = currentValue.subtract(amountInvested);
//
//        // ✅ Avoid division by zero when calculating performance
//        BigDecimal performance = BigDecimal.ZERO;
//        if (amountInvested.compareTo(BigDecimal.ZERO) > 0) {
//            performance = returnsGenerated
//                    .divide(amountInvested, 2, RoundingMode.HALF_UP)
//                    .multiply(BigDecimal.valueOf(100));
//        }
//
//        // ✅ Use DTO format first
//        InvestmentHistoryDTO dto = new InvestmentHistoryDTO();
//        dto.setInvestmentId(investment.getInvestmentId());
//        dto.setCurrentValue(currentValue);
//        dto.setPerformance(performance);
//        dto.setReturnsGenerated(returnsGenerated);
//        dto.setRecordedAt(LocalDateTime.now());
//
//        // ✅ Convert DTO to Entity using the mapper
//        InvestmentHistory history = investmentHistoryMapper.toEntity(dto, investment);
//
//        investmentHistoryRepository.save(history);
//    }
}
