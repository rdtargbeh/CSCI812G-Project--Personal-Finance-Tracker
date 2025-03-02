package csci812_project.backend.repository;

import csci812_project.backend.entity.Transaction;
import csci812_project.backend.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /** Find all transactions by user ID */
    List<Transaction> findByUser_UserId(Long userId);

    /** Find all transactions for a specific account */
    List<Transaction> findByAccount_AccountId(Long accountId);

    /** Find all transactions by transaction type */
    List<Transaction> findByTransactionType(TransactionType transactionType);

    /** Find recurring transactions that are due */
    List<Transaction> findByIsRecurringTrueAndNextDueDateBefore(LocalDateTime now);

    /**
     * ✅ Calculate Total Income within a Date Range
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user.userId = :userId AND t.transactionType = 'INCOME' AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal calculateTotalIncome(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * ✅ Calculate Total Expenses within a Date Range
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user.userId = :userId AND t.transactionType = 'EXPENSE' AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal calculateTotalExpense(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}

