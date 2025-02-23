package csci812_project.backend.repository;

import csci812_project.backend.entity.Transaction;
import csci812_project.backend.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

}

