package csci812_project.backend.service.implement;

import csci812_project.backend.dto.BudgetDTO;
import csci812_project.backend.entity.Budget;
import csci812_project.backend.entity.Category;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.BudgetType;
import csci812_project.backend.exception.NotFoundException;
import csci812_project.backend.mapper.BudgetMapper;
import csci812_project.backend.repository.BudgetRepository;
import csci812_project.backend.repository.CategoryRepository;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetServiceImplementation implements BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BudgetMapper budgetMapper;


    @Override
    public BudgetDTO createBudget(BudgetDTO budgetDTO) {
        User user = userRepository.findById(budgetDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Category category = categoryRepository.findById(budgetDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        Budget budget = budgetMapper.toEntity(budgetDTO, user, category);
        budget.setUser(user);
        budget.setCategory(category);

        return budgetMapper.toDTO(budgetRepository.save(budget));
    }

    @Override
    public BudgetDTO getBudgetById(Long budgetId) {
        Budget budget = budgetRepository.findById(budgetId)
                .filter(a -> !a.isDeleted())  // excludes soft deleted budgets
                .orElseThrow(() -> new NotFoundException("Budget not found"));
        return budgetMapper.toDTO(budget);

    }

    @Override
    public List<BudgetDTO> getBudgetsByUser(Long userId) {
        return budgetRepository.findByUser_UserId(userId)
                .stream()
                .filter(budget -> !budget.isDeleted())   // ✅ Exclude soft-deleted accounts
                .map(budgetMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BudgetDTO updateBudget(Long id, BudgetDTO budgetDTO) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Budget not found"));

        budget.setAmountLimit(budgetDTO.getAmountLimit());
        budget.setStartDate(budgetDTO.getStartDate());
        budget.setEndDate(budgetDTO.getEndDate());
        budget.setDescription(budget.getDescription());
        budget.setBudgetType(BudgetType.valueOf(budgetDTO.getBudgetType()));

        return budgetMapper.toDTO(budgetRepository.save(budget));
    }

    @Override
    public void deleteBudget(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Budget not found"));

        budget.setDeleted(true);
        budgetRepository.save(budget);
    }

    /**
     * ✅ Check if the user is close to exceeding their budget (80% usage threshold).
     */
    @Override
    public boolean checkBudgetUsage(Long userId, Long categoryId, BigDecimal transactionAmount) {
        List<Budget> budgets = budgetRepository.findByUser_UserIdAndCategory_CategoryId(userId, categoryId);

        for (Budget budget : budgets) {
            BigDecimal spentAmount = budgetRepository.getTotalSpentInBudget(userId, categoryId);
            BigDecimal newTotalSpent = spentAmount.add(transactionAmount);
            BigDecimal alertThreshold = budget.getAmountLimit().multiply(BigDecimal.valueOf(0.8));

            if (newTotalSpent.compareTo(alertThreshold) >= 0) {
                return true; // Trigger an alert
            }
        }
        return false;
    }
}
