package csci812_project.backend.service.implement;

import csci812_project.backend.dto.InvestmentDTO;
import csci812_project.backend.entity.Investment;
import csci812_project.backend.entity.User;
import csci812_project.backend.mapper.InvestmentMapper;
import csci812_project.backend.repository.InvestmentRepository;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.service.InvestmentService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvestmentServiceImplementation implements InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final InvestmentMapper investmentMapper;
    private final UserRepository userRepository;

    @Autowired
    public InvestmentServiceImplementation(InvestmentRepository investmentRepository,
                                           InvestmentMapper investmentMapper,
                                           UserRepository userRepository) {
        this.investmentRepository = investmentRepository;
        this.investmentMapper = investmentMapper;
        this.userRepository = userRepository;
    }

    @Override
    public InvestmentDTO addInvestment(InvestmentDTO investmentDTO) {
        User user = userRepository.findById(investmentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Investment investment = investmentMapper.toEntity(investmentDTO, user);
        investment = investmentRepository.save(investment);
        return investmentMapper.toDTO(investment);
    }

    @Scheduled(cron = "0 0 12 * * ?") // ✅ Runs daily at noon
    public void updateAllInvestments() {
        List<Investment> investments = investmentRepository.findAll(); // ✅ Fetch all investments

        for (Investment investment : investments) {
            // ✅ Perform investment updates (Example: Simulating market fluctuations)
            BigDecimal newMarketValue = investment.getCurrentValue().multiply(BigDecimal.valueOf(1.02)); // Simulated 2% increase
            investment.setCurrentValue(newMarketValue);
            investment.setLastUpdated(LocalDateTime.now());

            investmentRepository.save(investment);
        }

        System.out.println("✅ Scheduled investment updates completed.");
    }

//    @Override
//    @Scheduled(cron = "0 0 12 * * ?") // Runs daily at noon
//    public InvestmentDTO updateInvestment(Long id, InvestmentDTO investmentDTO) {
//        Investment investment = investmentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Investment not found"));
//
//        investment.setAssetName(investmentDTO.getAssetName());
//        investment.setAmountInvested(investmentDTO.getAmountInvested());
//        investment.setCurrentValue(investmentDTO.getCurrentValue());
//        investment.setInvestmentType(investmentDTO.getInvestmentType());
//        investment.setLastUpdated(LocalDateTime.now());
//
//        investmentRepository.save(investment); // ✅ `@PreUpdate` triggers performance calculation
//
//        return investmentMapper.toDTO(investment);
//    }


    @Override
    public InvestmentDTO getInvestmentById(Long id) {
        Investment investment = investmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investment not found"));
        return investmentMapper.toDTO(investment);
    }

    @Override
    public List<InvestmentDTO> getInvestmentsByUser(Long userId) {
        return investmentRepository.findByUser_UserId(userId)
                .stream()
                .map(investmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteInvestment(Long id) {
        Investment investment = investmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investment not found"));
        investment.setDeleted(true);
        investmentRepository.save(investment);
    }

    @Override
    public void restoreInvestment(Long id) {
        Investment investment = investmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investment not found"));
        investment.setDeleted(false);
        investmentRepository.save(investment);
    }

}

