package csci812_project.backend.mapper;

import csci812_project.backend.dto.BudgetDTO;
import csci812_project.backend.entity.Budget;
import csci812_project.backend.entity.Category;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.BudgetType;
import csci812_project.backend.exception.NotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BudgetMapper {

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

}
