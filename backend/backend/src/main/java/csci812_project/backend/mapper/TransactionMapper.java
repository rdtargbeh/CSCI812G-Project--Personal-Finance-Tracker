package csci812_project.backend.mapper;


import csci812_project.backend.dto.TransactionDTO;
import csci812_project.backend.entity.Transaction;
import csci812_project.backend.enums.PaymentMethod;
import csci812_project.backend.enums.TransactionStatus;
import csci812_project.backend.enums.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionDTO toDTO(Transaction transaction) {
        if (transaction == null) return null;
        return TransactionDTO.builder()
                .transactionId(transaction.getTransactionId())
                .userId(transaction.getUser().getUserId())
                .accountId(transaction.getAccount().getAccountId())
                .toAccountId(transaction.getToAccount() != null ? transaction.getToAccount().getAccountId() : null) // Handle transfers
                .amount(transaction.getAmount())
                .transactionType(transaction.getTransactionType().name())
                .paymentMethod(transaction.getPaymentMethod().name())
                .description(transaction.getDescription())
                .status(transaction.getStatus().name())
                .recurring(transaction.isRecurring())
                .dateCreated(transaction.getDateCreated())
                .build();
    }

    public Transaction toEntity(TransactionDTO transactionDTO) {
        if (transactionDTO == null) return null;
        return Transaction.builder()
                .amount(transactionDTO.getAmount())
                .transactionType(TransactionType.valueOf(transactionDTO.getTransactionType()))
                .paymentMethod(PaymentMethod.valueOf(transactionDTO.getPaymentMethod()))
                .description(transactionDTO.getDescription())
                .status(TransactionStatus.valueOf(transactionDTO.getStatus()))
                .recurring(transactionDTO.isRecurring())
                .dateCreated(transactionDTO.getDateCreated())
                .build();
    }
}

