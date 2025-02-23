package csci812_project.backend.service;

import csci812_project.backend.dto.InvestmentDTO;

import java.util.List;

public interface InvestmentService {
    InvestmentDTO addInvestment(InvestmentDTO investmentDTO);

    InvestmentDTO updateInvestment(Long id, InvestmentDTO investmentDTO);

    InvestmentDTO getInvestmentById(Long id);

    List<InvestmentDTO> getInvestmentsByUser(Long userId);

    void deleteInvestment(Long id);

    void restoreInvestment(Long id);


}
