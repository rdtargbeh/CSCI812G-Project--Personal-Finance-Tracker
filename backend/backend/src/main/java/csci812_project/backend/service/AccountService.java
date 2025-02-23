package csci812_project.backend.service;

import csci812_project.backend.dto.AccountDTO;
import java.util.List;

public interface AccountService {

    AccountDTO createAccount(Long userId, AccountDTO accountDTO);

    AccountDTO getAccountById(Long accountId);

    List<AccountDTO> getAccountsByUser(Long userId);

    AccountDTO updateAccount(Long accountId, AccountDTO accountDTO);

    void deleteAccount(Long accountId);

    void restoreAccount(Long accountId);
}

