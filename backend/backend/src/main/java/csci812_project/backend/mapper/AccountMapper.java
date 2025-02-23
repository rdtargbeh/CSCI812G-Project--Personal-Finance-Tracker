package csci812_project.backend.mapper;

import csci812_project.backend.dto.AccountDTO;
import csci812_project.backend.entity.Account;
import csci812_project.backend.enums.AccountType;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountDTO toDTO(Account account) {
        if (account == null) return null;
        return AccountDTO.builder()
                .accountId(account.getAccountId())
                .userId(account.getUser().getUserId())
                .name(account.getName())
                .type(account.getType().name())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .institutionName(account.getInstitutionName())
                .accountNumber(account.getAccountNumber())
                .interestRate(account.getInterestRate())
                .isDefault(account.isDefault())
                .isDeleted(account.isDeleted())
                .dateCreated(account.getDateCreated())
                .dateUpdated(account.getDateUpdated())
                .build();
    }

    public Account toEntity(AccountDTO accountDTO) {
        if (accountDTO == null) return null;
        return Account.builder()
                .accountId(accountDTO.getAccountId())
                .name(accountDTO.getName())
                .type(AccountType.valueOf(accountDTO.getType()))
                .balance(accountDTO.getBalance())
                .currency(accountDTO.getCurrency())
                .institutionName(accountDTO.getInstitutionName())
                .accountNumber(accountDTO.getAccountNumber())
                .interestRate(accountDTO.getInterestRate())
                .isDefault(accountDTO.isDefault())
                .isDeleted(accountDTO.isDeleted())
                .dateCreated(accountDTO.getDateCreated())
                .dateUpdated(accountDTO.getDateUpdated())
                .build();
    }
}

