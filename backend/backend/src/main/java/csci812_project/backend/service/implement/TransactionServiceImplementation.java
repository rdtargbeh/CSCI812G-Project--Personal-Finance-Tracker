package csci812_project.backend.service.implement;

import csci812_project.backend.dto.TransactionDTO;
import csci812_project.backend.dto.TransactionReportDTO;
import csci812_project.backend.entity.Account;
import csci812_project.backend.entity.Category;
import csci812_project.backend.entity.Transaction;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.RecurringInterval;
import csci812_project.backend.enums.TransactionType;
import csci812_project.backend.exception.NotFoundException;
import csci812_project.backend.mapper.TransactionMapper;
import csci812_project.backend.repository.AccountRepository;
import csci812_project.backend.repository.CategoryRepository;
import csci812_project.backend.repository.TransactionRepository;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.service.BudgetService;
import csci812_project.backend.service.EmailService;
import csci812_project.backend.service.TransactionService;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImplementation implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public TransactionDTO deposit(Long userId, Long accountId, TransactionDTO transactionDTO) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // ✅ Fetch Category using `categoryId` (instead of passing it in `toEntity()`)
        Category category = categoryRepository.findById(transactionDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        account.setBalance(account.getBalance().add(transactionDTO.getAmount()));

        Transaction transaction = transactionMapper.toEntity(transactionDTO, account.getUser(), account, null);
        transaction.setUser(account.getUser());
        transaction.setAccount(account);
        transaction.setDate(transaction.getDate());
        transaction.setCategory(category);

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

        // ✅ Fetch Category
        Category category = categoryRepository.findById(transactionDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // ✅ Fetch Parent Transaction if `parentTransactionId` is provided
        Transaction parentTransaction = null;
        if (transactionDTO.getParentTransactionId() != null) {
            parentTransaction = transactionRepository.findById(transactionDTO.getParentTransactionId())
                    .orElseThrow(() -> new RuntimeException("Parent transaction not found"));
        }

        account.setBalance(account.getBalance().subtract(transactionDTO.getAmount()));

        Transaction transaction = transactionMapper.toEntity(transactionDTO, account.getUser(), account, null);
        transaction.setUser(account.getUser());
        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setParentTransaction(parentTransaction);


        transactionRepository.save(transaction);
        accountRepository.save(account);
        return transactionMapper.toDTO(transaction);
    }

    @Override
    public TransactionDTO transfer(Long userId, Long fromAccountId, Long toAccountId, TransactionDTO transactionDTO) {
        if (toAccountId == null) {
            throw new RuntimeException("Destination account (toAccountId) must not be null!");
        }
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("From account not found"));

        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("To account not found"));

        if (fromAccount.getBalance().compareTo(transactionDTO.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(transactionDTO.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(transactionDTO.getAmount()));

        Transaction transaction = transactionMapper.toEntity(transactionDTO, fromAccount.getUser(), fromAccount, toAccount);
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
        // ✅ Check if the user exists before fetching accounts
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with ID " + userId + " not found"); // ✅ Throws a 404 error
        }

        List<Transaction> transactions = transactionRepository.findByUser_UserId(userId);
        return transactions.stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves all transactions for a specific account.
     */
    @Override
    public List<TransactionDTO> getTransactionsByAccount(Long accountId) {
        // ✅ Check if the user exists before fetching accounts
        if (!accountRepository.existsById(accountId)) {
            throw new NotFoundException("Account with ID " + accountId + " not found"); // ✅ Throws a 404 error
        }

        List<Transaction> transactions = transactionRepository.findByAccount_AccountId(accountId);
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
                Long accountId = Long.parseLong(record.get("Account ID"));
                BigDecimal amount = new BigDecimal(record.get("Amount"));
                TransactionType transactionType = TransactionType.valueOf(record.get("Transaction Type").toUpperCase());
                String description = record.get("Description");

                Account account = accountRepository.findById(accountId)
                        .orElseThrow(() -> new RuntimeException("Account not found"));

                TransactionDTO transactionDTO = new TransactionDTO();
                transactionDTO.setUserId(user.getUserId());
                transactionDTO.setAccountId(account.getAccountId());
                transactionDTO.setAmount(amount);
                transactionDTO.setTransactionType(transactionType.name());
                transactionDTO.setDescription(description);

                // ✅ Pass `transactionDTO`, `user`, and `account` to `toEntity()`
                Transaction transaction = transactionMapper.toEntity(transactionDTO, user, account, null);

                transactionRepository.save(transaction);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing CSV file: " + e.getMessage());
        }
    }


    @Override
    @Transactional
    public TransactionDTO addManualTransaction(TransactionDTO transactionDTO) {
        User user = userRepository.findById(transactionDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = accountRepository.findById(transactionDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Category category = categoryRepository.findById(transactionDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // ✅ Handle Parent Transaction (if applicable)
        Transaction parentTransaction = null;
        if (transactionDTO.getParentTransactionId() != null) {
            parentTransaction = transactionRepository.findById(transactionDTO.getParentTransactionId())
                    .orElseThrow(() -> new RuntimeException("Parent transaction not found"));
        }

        // ✅ Convert DTO to Entity
        Transaction transaction = transactionMapper.toEntity(transactionDTO, user, account, null);
        transaction.setUser(user);
        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setParentTransaction(parentTransaction);  // ✅ Set Parent Transaction

        transaction = transactionRepository.save(transaction);

        // ✅ Check if Budget Limit Exceeded
        boolean isBudgetExceeded = budgetService.checkBudgetUsage(
                transaction.getUser().getUserId(),
                transaction.getCategory().getCategoryId(),
                transaction.getAmount()
        );

        if (isBudgetExceeded) {
            try {
                emailService.sendBudgetAlert(user.getEmail(), category.getName(), transaction.getAmount());
                System.out.println("⚠️ ALERT: Budget limit warning email sent to " + user.getEmail());
            } catch (Exception e) {
                System.err.println("⚠️ ERROR: Failed to send budget alert email: " + e.getMessage());
            }
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
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setUserId(transaction.getUser().getUserId());
            transactionDTO.setAccountId(transaction.getAccount().getAccountId());
            transactionDTO.setAmount(transaction.getAmount());
            transactionDTO.setTransactionType(transaction.getTransactionType().name());
            transactionDTO.setDescription(transaction.getDescription());
            transactionDTO.setRecurring(false);
            transactionDTO.setDateCreated(now);

            // ✅ Pass `transactionDTO`, `transaction.getUser()`, `transaction.getAccount()` to `toEntity()`
            Transaction newTransaction = transactionMapper.toEntity(transactionDTO, transaction.getUser(), transaction.getAccount(), null);

            transactionRepository.save(newTransaction);
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

        Category category = categoryRepository.findById(transactionDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        RecurringInterval recurringInterval = RecurringInterval.valueOf(transactionDTO.getRecurringInterval().toUpperCase());

        // ✅ Set recurring values
        transactionDTO.setRecurring(true);
        transactionDTO.setDateCreated(LocalDateTime.now());
        transactionDTO.setNextDueDate(LocalDateTime.now().plusDays(1)); // Start from tomorrow

        // ✅ Convert DTO to Entity with Correct Arguments
        Transaction transaction = transactionMapper.toEntity(transactionDTO, user, account, null);
        transaction.setRecurringInterval(recurringInterval);

        transaction = transactionRepository.save(transaction);

        // ✅ Check if the budget limit is about to be exceeded
        boolean isBudgetExceeded = budgetService.checkBudgetUsage(
                transaction.getUser().getUserId(),
                transactionDTO.getCategoryId(),
                transaction.getAmount()
        );

        if (isBudgetExceeded) {
            // ✅ Send budget alert email
            emailService.sendBudgetAlert(user.getEmail(), category.getName(), transaction.getAmount());
            System.out.println("⚠️ ALERT: Budget limit warning email sent to " + user.getEmail());
        }

        return transactionMapper.toDTO(transaction);
    }



}
