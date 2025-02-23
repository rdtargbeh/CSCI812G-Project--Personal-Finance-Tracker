package csci812_project.backend.service;

import csci812_project.backend.dto.BudgetDTO;

import java.math.BigDecimal;
import java.util.List;

public interface BudgetService {
    BudgetDTO createBudget(BudgetDTO budgetDTO);

    BudgetDTO getBudgetById(Long id);

    List<BudgetDTO> getBudgetsByUser(Long userId);

    BudgetDTO updateBudget(Long id, BudgetDTO budgetDTO);

    void deleteBudget(Long id);

    boolean checkBudgetUsage(Long userId, Long categoryId, BigDecimal transactionAmount);

}

