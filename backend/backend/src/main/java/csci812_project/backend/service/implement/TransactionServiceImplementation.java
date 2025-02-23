package csci812_project.backend.service.implement;

import csci812_project.backend.dto.TransactionDTO;
import csci812_project.backend.entity.Account;
import csci812_project.backend.entity.Category;
import csci812_project.backend.entity.Transaction;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.RecurringInterval;
import csci812_project.backend.enums.TransactionType;
import csci812_project.backend.mapper.TransactionMapper;
import csci812_project.backend.repository.AccountRepository;
import csci812_project.backend.repository.CategoryRepository;
import csci812_project.backend.repository.TransactionRepository;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.service.BudgetService;
import csci812_project.backend.service.EmailService;
import csci812_project.backend.service.TransactionService;
import csci812_project.backend.entity.Login;
import csci812_project.backend.repository.LoginRepository;


import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionMapper transactionMapper;
    private final BudgetService budgetService;
    private final EmailService emailService;
    private final LoginRepository loginRepository; // ✅ Inject LoginRepository

    private final CategoryRepository categoryRepository;

    @Override
    public TransactionDTO deposit(Long userId, Long accountId, TransactionDTO transactionDTO) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(transactionDTO.getAmount()));

        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction.setUser(account.getUser());
        transaction.setAccount(account);

        transactionRepository.save(transaction);
        accountRepository.save(account);
        return transactionMapper.toDTO(transaction);
    }

    @Override
    public TransactionDTO withdraw(Long userId, Long accountId, TransactionDTO transactionDTO) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance().compareTo(transactionDTO.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(transactionDTO.getAmount()));

        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction.setUser(account.getUser());
        transaction.setAccount(account);

        transactionRepository.save(transaction);
        accountRepository.save(account);
        return transactionMapper.toDTO(transaction);
    }

    @Override
    public TransactionDTO transfer(Long userId, Long fromAccountId, Long toAccountId, TransactionDTO transactionDTO) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("From account not found"));

        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("To account not found"));

        if (fromAccount.getBalance().compareTo(transactionDTO.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(transactionDTO.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(transactionDTO.getAmount()));

        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction.setUser(fromAccount.getUser());
        transaction.setAccount(fromAccount);
        transaction.setToAccount(toAccount);

        transactionRepository.save(transaction);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        return transactionMapper.toDTO(transaction);
    }

    /**
     * Retrieves all transactions for a specific user.
     */
    @Override
    public List<TransactionDTO> getTransactionsByUser(Long userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        return transactions.stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all transactions for a specific account.
     */
    @Override
    public List<TransactionDTO> getTransactionsByAccount(Long accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);
        return transactions.stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getTransactionsByType(String transactionType) {
        try {
            TransactionType type = TransactionType.valueOf(transactionType.toUpperCase());
            List<Transaction> transactions = transactionRepository.findByTransactionType(type);
            return transactions.stream()
                    .map(transactionMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid transaction type: " + transactionType);
        }
    }

    @Override
    public void importTransactionsFromCSV(MultipartFile file, Long userId) {
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            for (CSVRecord record : csvParser) {
                String accountIdStr = record.get("Account ID");
                String amountStr = record.get("Amount");
                String transactionTypeStr = record.get("Transaction Type");
                String description = record.get("Description");

                Long accountId = Long.parseLong(accountIdStr);
                BigDecimal amount = new BigDecimal(amountStr);
                TransactionType transactionType = TransactionType.valueOf(transactionTypeStr.toUpperCase());

                Account account = accountRepository.findById(accountId)
                        .orElseThrow(() -> new RuntimeException("Account not found"));

                Transaction transaction = Transaction.builder()
                        .user(user)
                        .account(account)
                        .amount(amount)
                        .transactionType(transactionType)
                        .description(description)
                        .dateCreated(LocalDateTime.now())
                        .build();

                transactionRepository.save(transaction);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing CSV file: " + e.getMessage());
        }
    }

    @Override
    public TransactionDTO addManualTransaction(TransactionDTO transactionDTO) {
        User user = userRepository.findById(transactionDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = accountRepository.findById(transactionDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Category category = categoryRepository.findById(transactionDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // ✅ Fetch the user's email from the Login entity
        Login login = loginRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("User login details not found"));

        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        transaction.setUser(user);
        transaction.setAccount(account);
        transaction.setCategory(category);

        transaction = transactionRepository.save(transaction);

        // ✅ Check if the budget limit is about to be exceeded
        boolean isBudgetExceeded = budgetService.checkBudgetUsage(
                transaction.getUser().getUserId(),
                transaction.getCategory().getCategoryId(),
                transaction.getAmount()
        );

        if (isBudgetExceeded) {
            // ✅ Use email from Login entity
            emailService.sendBudgetAlert(login.getEmail(), category.getName(), transaction.getAmount());
            System.out.println("⚠️ ALERT: Budget limit warning email sent to " + login.getEmail());
        }

        return transactionMapper.toDTO(transaction);
    }


    /**
     * Runs every day at midnight to check and create recurring transactions.
     */
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at 12 AM
    @Override
    public void processRecurringTransactions() {
        LocalDateTime now = LocalDateTime.now();
        List<Transaction> recurringTransactions = transactionRepository.findByIsRecurringTrueAndNextDueDateBefore(now);
        for (Transaction transaction : recurringTransactions) {
            Transaction newTransaction = Transaction.builder()
                    .user(transaction.getUser())
                    .account(transaction.getAccount())
                    .amount(transaction.getAmount())
                    .transactionType(transaction.getTransactionType())
                    .description(transaction.getDescription())
                    .isRecurring(false) // The new transaction itself is not recurring
                    .dateCreated(now)
                    .build();

            transactionRepository.save(newTransaction);

            // Update the next due date
            transaction.setNextDueDate(calculateNextDueDate(transaction));
            transactionRepository.save(transaction);
        }
    }

    private LocalDateTime calculateNextDueDate(Transaction transaction) {
        switch (transaction.getRecurringInterval()) {
            case DAILY:
                return transaction.getNextDueDate().plusDays(1);
            case WEEKLY:
                return transaction.getNextDueDate().plusWeeks(1);
            case MONTHLY:
                return transaction.getNextDueDate().plusMonths(1);
            case YEARLY:
                return transaction.getNextDueDate().plusYears(1);
            default:
                return transaction.getNextDueDate();
        }
    }

    @Override
    public TransactionDTO createRecurringTransaction(TransactionDTO transactionDTO) {
        User user = userRepository.findById(transactionDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = accountRepository.findById(transactionDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        RecurringInterval recurringInterval = RecurringInterval.valueOf(transactionDTO.getRecurringInterval().toUpperCase());

        // ✅ Fetch category details using categoryId
        Category category = categoryRepository.findById(transactionDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // ✅ Fetch the user's email from the Login entity
        Login login = loginRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("User login details not found"));

        Transaction transaction = Transaction.builder()
                .user(user)
                .account(account)
                .amount(transactionDTO.getAmount())
                .transactionType(TransactionType.valueOf(transactionDTO.getTransactionType().toUpperCase()))
                .description(transactionDTO.getDescription())
                .isRecurring(true)
                .recurringInterval(recurringInterval)
                .nextDueDate(LocalDateTime.now().plusDays(1)) // Start from tomorrow
                .dateCreated(LocalDateTime.now())
                .build();

        transaction = transactionRepository.save(transaction);

        // ✅ Check if the budget limit is about to be exceeded
        boolean isBudgetExceeded = budgetService.checkBudgetUsage(
                transaction.getUser().getUserId(),
                transactionDTO.getCategoryId(),
                transaction.getAmount()
        );

        if (isBudgetExceeded) {
            // ✅ Use email from Login entity and fetch category name from `Category`
            emailService.sendBudgetAlert(login.getEmail(), category.getName(), transaction.getAmount());
            System.out.println("⚠️ ALERT: Budget limit warning email sent to " + login.getEmail());
        }

        return transactionMapper.toDTO(transaction);
    }


}
