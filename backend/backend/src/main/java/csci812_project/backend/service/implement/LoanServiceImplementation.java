package csci812_project.backend.service.implement;

import csci812_project.backend.dto.LoanDTO;
import csci812_project.backend.entity.Loan;
import csci812_project.backend.entity.User;
import csci812_project.backend.mapper.LoanMapper;
import csci812_project.backend.repository.LoanRepository;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplementation implements LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoanMapper loanMapper;


    @Override
    @Transactional
    public LoanDTO createLoan(LoanDTO loanDTO) {
        User user = userRepository.findById(loanDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Loan loan = loanMapper.toEntity(loanDTO, user);

        // ✅ Perform calculations before saving
        loan.calculateMonthlyPayment();
        loan.calculateTotalAmountPaid();
        loan.calculateTotalInterestPaid();
        loan.updateNextDueDate();
        loan.updateLoanStatus();

        loan = loanRepository.save(loan);
        return loanMapper.toDTO(loan);
    }


    @Override
    @Transactional
    public LoanDTO updateLoan(Long loanId, LoanDTO loanDTO) {
        Loan existingLoan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        Loan updatedLoan = loanMapper.toEntity(loanDTO, existingLoan.getUser());

        // ✅ Perform calculations before updating
        updatedLoan.calculateMonthlyPayment();
        updatedLoan.calculateTotalAmountPaid();
        updatedLoan.calculateTotalInterestPaid();
        updatedLoan.updateNextDueDate();
        updatedLoan.updateLoanStatus();

        updatedLoan.setLoanId(existingLoan.getLoanId());
        return loanMapper.toDTO(loanRepository.save(updatedLoan));
    }


    @Override
    @Transactional(readOnly = true)
    public LoanDTO getLoanById(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        return loanMapper.toDTO(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanDTO> getLoansByUser(Long userId) {
        return loanRepository.findByUser_UserId(userId)
                .stream()
                .map(loanMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void deleteLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loanRepository.delete(loan);
    }











}
