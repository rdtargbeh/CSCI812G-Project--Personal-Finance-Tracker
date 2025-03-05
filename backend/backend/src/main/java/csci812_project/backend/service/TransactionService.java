package csci812_project.backend.service;

import csci812_project.backend.dto.TransactionDTO;
import csci812_project.backend.dto.TransactionReportDTO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    TransactionDTO deposit(Long userId, Long accountId, TransactionDTO transactionDTO);

    TransactionDTO withdraw(Long userId, Long accountId, TransactionDTO transactionDTO);

    TransactionDTO transfer(Long userId, Long fromAccountId, Long toAccountId, TransactionDTO transactionDTO);

    List<TransactionDTO> getTransactionsByUser(Long userId);

    List<TransactionDTO> getTransactionsByAccount(Long accountId);

    /** Retrieves all transactions by transaction type */
    List<TransactionDTO> getTransactionsByType(String transactionType);

    void importTransactionsFromCSV(MultipartFile file, Long userId);

    TransactionDTO addManualTransaction(TransactionDTO transactionDTO);

    void processRecurringTransactions();

    TransactionDTO createRecurringTransaction(TransactionDTO transactionDTO);


}

