package csci812_project.backend.mapper;

import csci812_project.backend.dto.BudgetDTO;
import csci812_project.backend.entity.Budget;
import csci812_project.backend.enums.BudgetType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BudgetMapper {

    public BudgetDTO toDTO(Budget budget) {
        return BudgetDTO.builder()
                .budgetId(budget.getBudgetId())
                .userId(budget.getUser().getUserId())
                .categoryId(budget.getCategory().getCategoryId())
                .amountLimit(budget.getAmountLimit())
                .startDate(budget.getStartDate())
                .endDate(budget.getEndDate())
                .budgetType(budget.getBudgetType().name())
                .rolloverAmount(budget.getRolloverAmount())
                .isDeleted(budget.isDeleted())
                .dateCreated(budget.getDateCreated().atStartOfDay()) // ✅ Converts LocalDate → LocalDateTime
                .build();
    }

    public Budget toEntity(BudgetDTO budgetDTO) {
        return Budget.builder()
                .amountLimit(budgetDTO.getAmountLimit())
                .startDate(budgetDTO.getStartDate())
                .endDate(budgetDTO.getEndDate())
                .budgetType(BudgetType.valueOf(budgetDTO.getBudgetType()))
                .rolloverAmount(budgetDTO.getRolloverAmount())
                .isDeleted(budgetDTO.isDeleted())
                .dateCreated(budgetDTO.getDateCreated().toLocalDate()) // ✅ Converts LocalDateTime → LocalDate
                .build();
    }


}
