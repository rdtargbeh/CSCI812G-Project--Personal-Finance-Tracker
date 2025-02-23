package csci812_project.backend.controller;

import csci812_project.backend.dto.LoanDTO;
import csci812_project.backend.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO loanDTO) {
        return ResponseEntity.ok(loanService.createLoan(loanDTO));
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.getLoanById(loanId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanDTO>> getLoansByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(loanService.getLoansByUser(userId));
    }

    @PutMapping("/{loanId}")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable Long loanId, @RequestBody LoanDTO loanDTO) {
        return ResponseEntity.ok(loanService.updateLoan(loanId, loanDTO));
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long loanId) {
        loanService.deleteLoan(loanId);
        return ResponseEntity.noContent().build();
    }
}
