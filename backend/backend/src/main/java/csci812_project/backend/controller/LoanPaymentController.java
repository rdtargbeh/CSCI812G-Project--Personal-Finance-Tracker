package csci812_project.backend.controller;

import csci812_project.backend.dto.LoanPaymentDTO;
import csci812_project.backend.dto.LoanPaymentRequest;
import csci812_project.backend.service.LoanPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-payments")
public class LoanPaymentController {

    @Autowired
    private LoanPaymentService loanPaymentService;
//
//    public LoanPaymentController(LoanPaymentService loanPaymentService) {
//        this.loanPaymentService = loanPaymentService;
//    }


    @PostMapping("/{loanId}/pay")
    public ResponseEntity<LoanPaymentDTO> makePayment(
            @PathVariable Long loanId,
            @RequestBody LoanPaymentRequest paymentRequest) {

        LoanPaymentDTO paymentResponse = loanPaymentService.makePayment(
                loanId,
                paymentRequest.getPaymentAmount(),
                paymentRequest.getExtraPayment()
        );

        return ResponseEntity.ok(paymentResponse);
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

//    @PostMapping("/{loanId}/pay")
//    public ResponseEntity<String> makePayment(@PathVariable Long loanId, @RequestBody Map<String, BigDecimal> request) {
//        BigDecimal paymentAmount = request.get("paymentAmount");
//        BigDecimal extraPayment = request.get("extraPayment");
//
//        loanService.makePayment(loanId, paymentAmount, extraPayment);
//
//        return ResponseEntity.ok("Payment processed successfully!");
//    }

//    @PostMapping("/process-payments")
//    public ResponseEntity<String> processLoanPayments() {
//        loanService.processLoanPayments();
//        return ResponseEntity.ok("Loan payments processed successfully.");
//    }

}

