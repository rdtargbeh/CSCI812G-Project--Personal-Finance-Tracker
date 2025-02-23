package csci812_project.backend.controller;

import csci812_project.backend.dto.InvestmentDTO;
import csci812_project.backend.service.InvestmentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/investments")
public class InvestmentController {

    private final InvestmentService investmentService;

    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    @PostMapping
    public ResponseEntity<InvestmentDTO> addInvestment(@RequestBody InvestmentDTO dto) {
        return ResponseEntity.ok(investmentService.addInvestment(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestmentDTO> getInvestment(@PathVariable Long id) {
        return ResponseEntity.ok(investmentService.getInvestmentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvestment(@PathVariable Long id) {
        investmentService.deleteInvestment(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<Void> restoreInvestment(@PathVariable Long id) {
        investmentService.restoreInvestment(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * ✅ Fetch investments by user ID
     * @param userId The ID of the user whose investments are retrieved.
     * @return List of investments for the user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InvestmentDTO>> getInvestmentsByUser(@PathVariable Long userId) {
        List<InvestmentDTO> investments = investmentService.getInvestmentsByUser(userId);
        return ResponseEntity.ok(investments);
    }

    /**
     * ✅ Update an existing investment
     * @param id The ID of the investment to update.
     * @param investmentDTO The new investment details.
     * @return The updated investment.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InvestmentDTO> updateInvestment(@PathVariable Long id, @RequestBody InvestmentDTO investmentDTO) {
        InvestmentDTO updatedInvestment = investmentService.updateInvestment(id, investmentDTO);
        return ResponseEntity.ok(updatedInvestment);
    }

}

