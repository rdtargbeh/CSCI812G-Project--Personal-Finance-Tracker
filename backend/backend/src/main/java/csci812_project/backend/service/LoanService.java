package csci812_project.backend.service;

import csci812_project.backend.dto.LoanDTO;

import java.util.List;

public interface LoanService {
    LoanDTO createLoan(LoanDTO loanDTO);

    LoanDTO getLoanById(Long loanId);

    List<LoanDTO> getLoansByUser(Long userId);

    LoanDTO updateLoan(Long loanId, LoanDTO loanDTO);

    void deleteLoan(Long loanId);
}
