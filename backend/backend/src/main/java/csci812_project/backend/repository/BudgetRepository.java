package csci812_project.backend.repository;

import csci812_project.backend.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    /** Find budgets by user ID */
    List<Budget> findByUserId(Long userId);

    /** ✅ Find budgets by user ID and category ID */
    List<Budget> findByUser_UserIdAndCategory_Id(Long userId, Long categoryId);

    /** ✅ Calculate the total amount spent within a budget */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.category.id = :categoryId AND t.user.userId = :userId")
    BigDecimal getTotalSpentInBudget(@Param("userId") Long userId, @Param("categoryId") Long categoryId);



    /** Find active budgets (not deleted) */
    List<Budget> findByIsDeletedFalse();
}
