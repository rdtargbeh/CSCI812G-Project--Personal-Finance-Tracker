package csci812_project.backend.controller;

import csci812_project.backend.dto.SavingsGoalDTO;
import csci812_project.backend.service.SavingsGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/savings-goals")
public class SavingsGoalController {

    @Autowired
    private SavingsGoalService savingsGoalService;


    /** ✅ Create a new savings goal */
    @PostMapping
    public ResponseEntity<SavingsGoalDTO> createSavingsGoal(@RequestBody SavingsGoalDTO savingsGoalDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(savingsGoalService.createSavingsGoal(savingsGoalDTO));
    }

    /** ✅ Get savings goal by ID */
    @GetMapping("/{id}")
    public ResponseEntity<SavingsGoalDTO> getSavingsGoalById(@PathVariable Long id) {
        return savingsGoalService.getSavingsGoalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** ✅ Get all savings goals for a user */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SavingsGoalDTO>> getSavingsGoalsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(savingsGoalService.getSavingsGoalsByUser(userId));
    }

    /** ✅ Update a savings goal */
    @PutMapping("/{id}")
    public ResponseEntity<SavingsGoalDTO> updateSavingsGoal(@PathVariable Long id, @RequestBody SavingsGoalDTO savingsGoalDTO) {
        return ResponseEntity.ok(savingsGoalService.updateSavingsGoal(id, savingsGoalDTO));
    }

    /** ✅ Delete a savings goal */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSavingsGoal(@PathVariable Long id) {
        savingsGoalService.deleteSavingsGoal(id);
        return ResponseEntity.noContent().build();
    }

    /** ✅ Contribute money to a savings goal */
    @PostMapping("/{id}/contribute")
    public ResponseEntity<SavingsGoalDTO> contributeToSavings(@PathVariable Long id, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(savingsGoalService.contributeToSavings(id, amount));
    }
}

