package csci812_project.backend.service;

import csci812_project.backend.dto.BudgetDTO;
import csci812_project.backend.dto.BudgetReportDTO;
import csci812_project.backend.entity.Budget;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BudgetService {
    BudgetDTO createBudget(BudgetDTO budgetDTO);

    BudgetDTO getBudgetById(Long id);

    List<BudgetDTO> getBudgetsByUser(Long userId);

    BudgetDTO updateBudget(Long id, BudgetDTO budgetDTO);

    void deleteBudget(Long id);

    boolean checkBudgetUsage(Long userId, Long categoryId, BigDecimal transactionAmount);

    BudgetReportDTO getBudgetReport(Long userId);

    Optional<Budget> findActiveBudget(Long userId, Long categoryId, LocalDate transactionDate);

    boolean isTransactionWithinBudget(Long userId, Long categoryId, BigDecimal transactionAmount, LocalDateTime transactionDate);
}

