package csci812_project.backend.service.implement;

import csci812_project.backend.dto.AccountDTO;
import csci812_project.backend.entity.Account;
import csci812_project.backend.entity.User;
import csci812_project.backend.mapper.AccountMapper;
import csci812_project.backend.repository.AccountRepository;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplementation implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public AccountDTO createAccount(Long userId, AccountDTO accountDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = accountMapper.toEntity(accountDTO);
        account.setUser(user);
        account = accountRepository.save(account);
        return accountMapper.toDTO(account);
    }

    @Override
    public AccountDTO getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return accountMapper.toDTO(account);
    }

    @Override
    public List<AccountDTO> getAccountsByUser(Long userId) {
        return accountRepository.findByUser_UserId(userId)
                .stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO updateAccount(Long accountId, AccountDTO accountDTO) {
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        existingAccount.setName(accountDTO.getName());
        existingAccount.setBalance(accountDTO.getBalance());

        accountRepository.save(existingAccount);
        return accountMapper.toDTO(existingAccount);
    }

    @Override
    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setDeleted(true);
        accountRepository.save(account);
    }

    @Override
    public void restoreAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setDeleted(false);
        accountRepository.save(account);
    }
}
