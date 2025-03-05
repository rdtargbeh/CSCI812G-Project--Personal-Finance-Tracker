package csci812_project.backend.service;

import csci812_project.backend.dto.InvestmentDTO;

import java.util.List;

public interface InvestmentService {
    InvestmentDTO addInvestment(InvestmentDTO investmentDTO);

    InvestmentDTO updateInvestment(Long investmentId, InvestmentDTO investmentDTO);

//    void updateAllInvestments();

    InvestmentDTO getInvestmentById(Long id);

    List<InvestmentDTO> getInvestmentsByUser(Long userId);

    void deleteInvestment(Long id);

    void restoreInvestment(Long id);

    void simulateInvestmentGrowth();


}
