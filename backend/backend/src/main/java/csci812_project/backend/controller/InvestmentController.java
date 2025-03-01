package csci812_project.backend.controller;

import csci812_project.backend.dto.InvestmentDTO;
import csci812_project.backend.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/investments")
public class InvestmentController {

    @Autowired
    private InvestmentService investmentService;

    @PostMapping
    public ResponseEntity<InvestmentDTO> addInvestment(@RequestBody InvestmentDTO dto) {
        return ResponseEntity.ok(investmentService.addInvestment(dto));
    }

    @GetMapping("/{investmentId}")
    public ResponseEntity<InvestmentDTO> getInvestmentById(@PathVariable Long investmentId) {
        return ResponseEntity.ok(investmentService.getInvestmentById(investmentId));
    }

    /**
     * ✅ Update an existing investment
     * @param investmentId The ID of the investment to update.
     * @param investmentDTO The new investment details.
     * @return The updated investment.
     */
    @PutMapping("/{investmentId}")
    public ResponseEntity<InvestmentDTO> updateInvestment(
            @PathVariable Long investmentId,
            @RequestBody InvestmentDTO investmentDTO) {

        InvestmentDTO updatedInvestment = investmentService.updateInvestment(investmentId, investmentDTO);
        return ResponseEntity.ok(updatedInvestment);
    }

    /**
     * ✅ Delete an existing investment
     * @param investmentId The ID of the investment to update.
     * @return The updated investment.
     */
    @DeleteMapping("/{investmentId}")
    public ResponseEntity<Void> deleteInvestment(@PathVariable Long investmentId) {
        investmentService.deleteInvestment(investmentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/restore/{investmentId}")
    public ResponseEntity<String> restoreInvestment(@PathVariable Long investmentId) {
        investmentService.restoreInvestment(investmentId);
        return ResponseEntity.ok("Investment restored successfully.");
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




}

