package csci812_project.backend.controller;

import csci812_project.backend.dto.LoanPaymentDTO;
import csci812_project.backend.service.LoanPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/loan-payments")
public class LoanPaymentController {

    @Autowired
    private LoanPaymentService loanPaymentService;

    public LoanPaymentController(LoanPaymentService loanPaymentService) {
        this.loanPaymentService = loanPaymentService;
    }

    @PostMapping("/{loanId}/pay")
    public ResponseEntity<LoanPaymentDTO> makePayment(@PathVariable Long loanId, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(loanPaymentService.makePayment(loanId, amount));
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<List<LoanPaymentDTO>> getPaymentsByLoan(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanPaymentService.getPaymentsByLoan(loanId));
    }

    /**
     * ✅ Trigger Loan Reminder Emails Manually
     */
    @PostMapping("/send-reminders")
    public ResponseEntity<String> triggerLoanReminders() {
        loanPaymentService.sendLoanPaymentReminders();
        return ResponseEntity.ok("Loan reminders sent!");
    }
}

