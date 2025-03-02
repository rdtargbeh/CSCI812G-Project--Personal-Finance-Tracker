package csci812_project.backend.mapper;


import csci812_project.backend.dto.TransactionDTO;
import csci812_project.backend.entity.Account;
import csci812_project.backend.entity.Category;
import csci812_project.backend.entity.Transaction;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.PaymentMethod;
import csci812_project.backend.enums.RecurringInterval;
import csci812_project.backend.enums.TransactionStatus;
import csci812_project.backend.enums.TransactionType;
import csci812_project.backend.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionMapper {

    public TransactionDTO toDTO(Transaction transaction) {
        if (transaction == null) return null;

        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setUserId(transaction.getUser().getUserId());
        dto.setAccountId(transaction.getAccount().getAccountId());
        dto.setToAccountId(transaction.getToAccount() != null ? transaction.getToAccount().getAccountId() : null); // Handle transfers
        dto.setCategoryId(transaction.getCategory() != null ? transaction.getCategory().getCategoryId() : null); // Ensure categoryId is set
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
        transaction.setUser(user);
        transaction.setAccount(account);
        transaction.setToAccount(toAccount);


        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionType(TransactionType.valueOf(transactionDTO.getTransactionType()));
        transaction.setDate(transactionDTO.getDate() != null ? transactionDTO.getDate() : LocalDateTime.now());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setPaymentMethod(transactionDTO.getPaymentMethod() != null ?
                PaymentMethod.valueOf(transactionDTO.getPaymentMethod()) : null);
        transaction.setReceiptUrl(transactionDTO.getReceiptUrl());
        transaction.setRecurring(transactionDTO.isRecurring());
        if (transactionDTO.getRecurringInterval() != null) {
            transaction.setRecurringInterval(RecurringInterval.valueOf(transactionDTO.getRecurringInterval().toUpperCase()));
        }

        transaction.setNextDueDate(transactionDTO.getNextDueDate());
        transaction.setParentTransaction(null);
        transaction.setStatus(transactionDTO.getStatus() != null ?
                TransactionStatus.valueOf(transactionDTO.getStatus()) : TransactionStatus.COMPLETED);
        transaction.setDeleted(false);
        transaction.setDateCreated(LocalDateTime.now());
        transaction.setDateUpdated(LocalDateTime.now());

        return transaction;
    }

//    public Transaction toEntity(TransactionDTO transactionDTO, User user, Account account, Account toAccount, Category category) {
//        if (transactionDTO == null) return null;
//
//        Transaction transaction = new Transaction();
//        transaction.setUser(user); // ✅ Assign User object
//        transaction.setAccount(account); // ✅ Assign Account object
//        transaction.setToAccount(toAccount); // ✅ Assign destination account for transfers
//        transaction.setCategory(category); // Ensure category is set
//
//        // ✅ Set category only if provided
//        if (category != null) {
//            transaction.setCategory(category);
//        }
//        transaction.setAmount(transactionDTO.getAmount());
//        transaction.setTransactionType(TransactionType.valueOf(transactionDTO.getTransactionType()));
//        transaction.setDescription(transactionDTO.getDescription());
//
//
//        if (transactionDTO.getPaymentMethod() != null) {
//            try {
//                transaction.setPaymentMethod(PaymentMethod.valueOf(transactionDTO.getPaymentMethod().toUpperCase()));
//            } catch (IllegalArgumentException e) {
//                throw new RuntimeException("Invalid payment method: " + transactionDTO.getPaymentMethod());
//            }
//        } else {
//            throw new RuntimeException("Payment method is required!");
//        }
//
//        // ✅ Set default status if null
//        if (transactionDTO.getStatus() != null) {
//            transaction.setStatus(TransactionStatus.valueOf(transactionDTO.getStatus().toUpperCase()));
//        } else {
//            transaction.setStatus(TransactionStatus.COMPLETED); // Default to COMPLETED
//        }
//
//        transaction.setRecurring(transactionDTO.isRecurring());
//        transaction.setDateCreated(transactionDTO.getDateCreated());
//        return transaction;
//    }

}

