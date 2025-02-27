package csci812_project.backend.service.implement;

import csci812_project.backend.dto.LoanPaymentDTO;
import csci812_project.backend.entity.Loan;
import csci812_project.backend.entity.LoanPayment;
import csci812_project.backend.enums.LoanStatus;
import csci812_project.backend.mapper.LoanPaymentMapper;
import csci812_project.backend.repository.LoanPaymentRepository;
import csci812_project.backend.repository.LoanRepository;
import csci812_project.backend.service.EmailService;
import csci812_project.backend.service.LoanPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanPaymentServiceImplementation implements LoanPaymentService {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanPaymentRepository loanPaymentRepository;
    @Autowired
    private LoanPaymentMapper loanPaymentMapper;
    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public LoanPaymentDTO makePayment(Long loanId, BigDecimal amount) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (amount.compareTo(loan.getOutstandingBalance()) > 0) {
            throw new RuntimeException("Payment exceeds outstanding balance");
        }

        // ✅ Update loan balance
        BigDecimal newBalance = loan.getOutstandingBalance().subtract(amount);
        loan.setOutstandingBalance(newBalance);

        // ✅ Update next due date
        loan.updateNextDueDate();

        // ✅ Mark loan as PAID_OFF if fully repaid
        if (newBalance.compareTo(BigDecimal.ZERO) == 0) {
            loan.setStatus(LoanStatus.PAID_OFF);
        }

        loanRepository.save(loan);

        // ✅ Create LoanPayment record
        LoanPayment loanPayment = new LoanPayment();
        loanPayment.initializePayment(loan, loan.getUser(), amount);

        loanPaymentRepository.save(loanPayment);

        return loanPaymentMapper.toDTO(loanPayment);
    }


    @Override
    @Transactional(readOnly = true)
    public List<LoanPaymentDTO> getPaymentsByLoan(Long loanId) {
        return loanPaymentRepository.findByLoan_LoanId(loanId)
                .stream()
                .map(loanPaymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * ✅ Run every day at 12 AM (Midnight) to check for upcoming payments
     */
    @Scheduled(cron = "0 0 0 * * ?") // Runs every midnight
    public void sendLoanPaymentReminders() {
        LocalDate reminderDate = LocalDate.now().plusDays(3); // Notify 3 days before due date
        List<LoanPayment> upcomingPayments = loanPaymentRepository.findByNextDueDate(reminderDate);

        for (LoanPayment payment : upcomingPayments) {
            String userEmail = payment.getUser().getEmail();
            String loanName = payment.getLoan().getLenderName();
            BigDecimal amountDue = payment.getPaymentAmount();
            String dueDate = payment.getNextDueDate().toString();

            emailService.sendLoanReminder(userEmail, loanName, dueDate, amountDue);
        }
    }
}

