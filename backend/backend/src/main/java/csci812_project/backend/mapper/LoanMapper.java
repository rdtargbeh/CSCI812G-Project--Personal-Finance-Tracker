package csci812_project.backend.mapper;

import csci812_project.backend.dto.LoanDTO;
import csci812_project.backend.entity.Loan;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.LoanStatus;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanDTO toDTO(Loan loan) {
        if (loan == null) return null;

        LoanDTO dto = new LoanDTO();
        dto.setLoanId(loan.getLoanId());
        dto.setUserId(loan.getUser().getUserId());
        dto.setLenderName(loan.getLenderName());
        dto.setAmountBorrowed(loan.getAmountBorrowed());
        dto.setOutstandingBalance(loan.getOutstandingBalance());
        dto.setInterestRate(loan.getInterestRate());
        dto.setInterestPaid(loan.getInterestPaid());
        dto.setMonthlyPayment(loan.getMonthlyPayment());
        dto.setTotalAmountPaid(loan.getTotalAmountPaid());
        dto.setNumberYears(loan.getNumberYears());
        dto.setDueDate(loan.getDueDate());
        dto.setStatus(loan.getStatus());

        return dto;
    }

    public Loan toEntity(LoanDTO dto, User user) {
        if (dto == null) return null;

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setLenderName(dto.getLenderName());
        loan.setAmountBorrowed(dto.getAmountBorrowed());
        loan.setOutstandingBalance(dto.getOutstandingBalance());
        loan.setInterestRate(dto.getInterestRate());
        loan.setInterestPaid(dto.getInterestPaid());
        loan.setMonthlyPayment(dto.getMonthlyPayment());
        loan.setTotalAmountPaid(dto.getTotalAmountPaid());
        loan.setNumberYears(dto.getNumberYears());
        loan.setDueDate(dto.getDueDate());
        loan.setStatus(dto.getStatus());

        return loan;
    }
}

