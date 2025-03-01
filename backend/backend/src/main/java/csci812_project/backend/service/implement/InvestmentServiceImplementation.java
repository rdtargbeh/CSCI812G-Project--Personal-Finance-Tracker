package csci812_project.backend.service.implement;

import csci812_project.backend.dto.InvestmentDTO;
import csci812_project.backend.entity.Investment;
import csci812_project.backend.entity.User;
import csci812_project.backend.exception.NotFoundException;
import csci812_project.backend.mapper.InvestmentMapper;
import csci812_project.backend.repository.InvestmentRepository;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.service.InvestmentService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvestmentServiceImplementation implements InvestmentService {

    @Autowired
    private InvestmentRepository investmentRepository;
    @Autowired
    private InvestmentMapper investmentMapper;
    @Autowired
    private UserRepository userRepository;



    @Override
    public InvestmentDTO addInvestment(InvestmentDTO investmentDTO) {
        User user = userRepository.findById(investmentDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Investment investment = investmentMapper.toEntity(investmentDTO, user);
        investment = investmentRepository.save(investment);
        return investmentMapper.toDTO(investment);
    }

    @Override
    public InvestmentDTO updateInvestment(Long investmentId, InvestmentDTO investmentDTO) {
        // ✅ Find the investment or throw an exception if not found
        Investment investment = investmentRepository.findById(investmentId)
                .orElseThrow(() -> new RuntimeException("Investment not found: " + investmentId));

        // ✅ Update investment fields
        investment.setAssetName(investmentDTO.getAssetName());
        investment.setInvestmentType(investmentDTO.getInvestmentType());
        investment.setAmountInvested(investmentDTO.getAmountInvested());
        investment.setCurrentValue(investmentDTO.getCurrentValue());
        investment.setLastUpdated(LocalDateTime.now()); // ✅ Update timestamp

        // ✅ Recalculate performance
        if (investment.getAmountInvested().compareTo(BigDecimal.ZERO) > 0) {
            investment.setPerformance(
                    investment.getCurrentValue().subtract(investment.getAmountInvested())
                            .divide(investment.getAmountInvested(), 2, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100))
            );
        } else {
            investment.setPerformance(BigDecimal.ZERO);
        }

        // ✅ Save and return updated investment
        investment = investmentRepository.save(investment);
        return investmentMapper.toDTO(investment);
    }


    @Override
    public InvestmentDTO getInvestmentById(Long investmentId) {
        Investment investment = investmentRepository.findById(investmentId)
                .filter(a -> !a.isDeleted())
                .orElseThrow(() -> new NotFoundException("Investment not found"));
        return investmentMapper.toDTO(investment);
    }

    @Override
    public List<InvestmentDTO> getInvestmentsByUser(Long userId) {
        return investmentRepository.findByUser_UserId(userId)
                .stream()
                .filter(a -> !a.isDeleted())
                .map(investmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteInvestment(Long investmentId) {
        Investment investment = investmentRepository.findById(investmentId)
                .orElseThrow(() -> new NotFoundException("Investment not found"));
        investment.setDeleted(true);
        investmentRepository.save(investment);
    }

    @Override
    public void restoreInvestment(Long investmentId) {
        Investment investment = investmentRepository.findById(investmentId)
                .orElseThrow(() -> new NotFoundException("Investment not found"));

        if (!investment.isDeleted()) {
            throw new NotFoundException("Investment is already active.");
        }
        investment.setDeleted(false); // ✅ Restore investment
        investmentRepository.save(investment);
    }

}

