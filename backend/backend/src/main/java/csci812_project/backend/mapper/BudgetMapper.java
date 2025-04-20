package csci812_project.backend.mapper;

import csci812_project.backend.dto.BudgetDTO;
import csci812_project.backend.dto.BudgetDetailsDTO;
import csci812_project.backend.entity.Budget;
import csci812_project.backend.entity.Category;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.BudgetType;
import java.math.RoundingMode;
import csci812_project.backend.exception.NotFoundException;
import csci812_project.backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class BudgetMapper {

    @Autowired
    private TransactionRepository transactionRepository;

    public BudgetDTO toDTO(Budget budget) {
        BudgetDTO dto = new BudgetDTO();
        dto.setBudgetId(budget.getBudgetId());
        dto.setUserId(budget.getUser().getUserId());
        dto.setCategoryId(budget.getCategory().getCategoryId());
        dto.setAmountLimit(budget.getAmountLimit());
        dto.setStartDate(budget.getStartDate());
        dto.setDescription(budget.getDescription());
        dto.setEndDate(budget.getEndDate());
        dto.setBudgetType(budget.getBudgetType().name());
        dto.setRolloverAmount(budget.getRolloverAmount());
        dto.setDeleted(budget.isDeleted());
        dto.setDateCreated(budget.getDateCreated() != null ? budget.getDateCreated().atStartOfDay() : null); // ✅ Handle null case
        return dto;
    }

    public Budget toEntity(BudgetDTO budgetDTO, User user, Category category) {
        Budget budget = new Budget();
        budget.setUser(user);  // ✅ Assign User object
        budget.setCategory(category);  // ✅ Assign Category object
        budget.setAmountLimit(budgetDTO.getAmountLimit());
        budget.setStartDate(budgetDTO.getStartDate());
        budget.setEndDate(budgetDTO.getEndDate());

        // ✅ Prevent NullPointerException when converting BudgetType
        if (budgetDTO.getBudgetType() != null) {
            try {
                budget.setBudgetType(BudgetType.valueOf(budgetDTO.getBudgetType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new NotFoundException("Invalid Budget Type: " + budgetDTO.getBudgetType());
            }
        } else {
            throw new NotFoundException("Budget Type cannot be null!");
        }

        budget.setRolloverAmount(budgetDTO.getRolloverAmount());
        budget.setDescription(budgetDTO.getDescription()); // ✅ Fix incorrect mapping
        budget.setDeleted(budgetDTO.isDeleted());

        // ✅ Safely handle `dateCreated`
        budget.setDateCreated(budgetDTO.getDateCreated() != null ? budgetDTO.getDateCreated().toLocalDate() : LocalDate.now());

        return budget;
    }

    public BudgetDetailsDTO toDetailsDTO(Budget budget) {
        BudgetDetailsDTO dto = new BudgetDetailsDTO();
        dto.setDescription(budget.getDescription());
        dto.setAmountLimit(budget.getAmountLimit());
        dto.setStartDate(budget.getStartDate());
        dto.setEndDate(budget.getEndDate());
        dto.setBudgetType(budget.getBudgetType().name());
        dto.setRolloverAmount(budget.getRolloverAmount());
        dto.setDateCreated(budget.getDateCreated().atStartOfDay()); // assumes LocalDate → LocalDateTime
        dto.setCategory(budget.getCategory().getName()); // or however your Category name is stored

        // 🔢 Calculate spent from transactions
        BigDecimal spent = transactionRepository.sumExpensesInCategoryForPeriod(
                budget.getUser().getUserId(),
                budget.getCategory().getCategoryId(),
                budget.getStartDate().atStartOfDay(),
                budget.getEndDate().atTime(LocalTime.MAX)
        );

        dto.setSpent(spent);

        int percentageUsed = budget.getAmountLimit().compareTo(BigDecimal.ZERO) > 0
                ? spent.multiply(BigDecimal.valueOf(100))
                .divide(budget.getAmountLimit(), 0, RoundingMode.HALF_UP)
                .intValue()
                : 0;

        dto.setPercentageUsed(percentageUsed);

        return dto;
    }

}
