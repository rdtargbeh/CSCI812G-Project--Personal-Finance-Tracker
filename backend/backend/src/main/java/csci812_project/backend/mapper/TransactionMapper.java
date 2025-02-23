package csci812_project.backend.mapper;


import csci812_project.backend.dto.TransactionDTO;
import csci812_project.backend.entity.Account;
import csci812_project.backend.entity.Transaction;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.PaymentMethod;
import csci812_project.backend.enums.TransactionStatus;
import csci812_project.backend.enums.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionDTO toDTO(Transaction transaction) {
        if (transaction == null) return null;

        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setUserId(transaction.getUser().getUserId());
        dto.setAccountId(transaction.getAccount().getAccountId());
        dto.setToAccountId(transaction.getToAccount() != null ? transaction.getToAccount().getAccountId() : null); // Handle transfers
        dto.setAmount(transaction.getAmount());
        dto.setTransactionType(transaction.getTransactionType().name());
        dto.setPaymentMethod(transaction.getPaymentMethod().name());
        dto.setDescription(transaction.getDescription());
        dto.setStatus(transaction.getStatus().name());
        dto.setRecurring(transaction.isRecurring());
        dto.setDateCreated(transaction.getDateCreated());
        return dto;
    }

    public Transaction toEntity(TransactionDTO transactionDTO, User user, Account account, Account toAccount) {
        if (transactionDTO == null) return null;

        Transaction transaction = new Transaction();
        transaction.setUser(user); // ✅ Assign User object
        transaction.setAccount(account); // ✅ Assign Account object
        transaction.setToAccount(toAccount); // ✅ Assign destination account for transfers
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionType(TransactionType.valueOf(transactionDTO.getTransactionType()));
        transaction.setPaymentMethod(PaymentMethod.valueOf(transactionDTO.getPaymentMethod()));
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setStatus(TransactionStatus.valueOf(transactionDTO.getStatus()));
        transaction.setRecurring(transactionDTO.isRecurring());
        transaction.setDateCreated(transactionDTO.getDateCreated());
        return transaction;
    }

}

