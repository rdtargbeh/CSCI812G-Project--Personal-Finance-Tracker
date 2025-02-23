package csci812_project.backend.service;

import csci812_project.backend.dto.LoanPaymentDTO;

import java.math.BigDecimal;
import java.util.List;

public interface LoanPaymentService {

    LoanPaymentDTO makePayment(Long loanId, BigDecimal amount);

    List<LoanPaymentDTO> getPaymentsByLoan(Long loanId);

    void sendLoanPaymentReminders();
}
