package csci812_project.backend.service.implement;

import csci812_project.backend.dto.AccountDTO;
import csci812_project.backend.entity.Account;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.AccountType;
import csci812_project.backend.exception.NotFoundException;
import csci812_project.backend.mapper.AccountMapper;
import csci812_project.backend.repository.AccountRepository;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.service.AccountService;
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
                .orElseThrow(() -> new NotFoundException("User not found"));

        Account account = accountMapper.toEntity(accountDTO);
        account.setUser(user);
        account = accountRepository.save(account);
        return accountMapper.toDTO(account);
    }

    @Override
    public AccountDTO getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .filter(a -> !a.isDeleted()) // ✅ Exclude soft-deleted accounts
                .orElseThrow(() -> new NotFoundException("Account not found or has been deleted"));

        return accountMapper.toDTO(account);
    }

    @Override
    public List<AccountDTO> getAccountsByUser(Long userId) {

        // ✅ Check if the user exists before fetching accounts
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with ID " + userId + " not found"); // ✅ Throws a 404 error
        }
        return accountRepository.findByUser_UserId(userId)
                .stream()
                .filter(account -> !account.isDeleted()) // ✅ Exclude soft-deleted accounts
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

//    Get All account Both Soft-Delete and Non-Delete
    @Override
    public List<AccountDTO> getAllAccountsByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with ID " + userId + " not found");
        }
        // ✅ Do not filter out deleted accounts
        return accountRepository.findByUser_UserId(userId)
                .stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO updateAccount(Long accountId, AccountDTO accountDTO) {
        Account existingAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        existingAccount.setName(accountDTO.getName());
        existingAccount.setType(AccountType.valueOf(accountDTO.getType().toUpperCase()));
        existingAccount.setAccountNumber(accountDTO.getAccountNumber());
        existingAccount.setBalance(accountDTO.getBalance());
        existingAccount.setInterestRate(accountDTO.getInterestRate());
        existingAccount.setInstitutionName(accountDTO.getInstitutionName());
        existingAccount.setCurrency(accountDTO.getCurrency());

        accountRepository.save(existingAccount);
        return accountMapper.toDTO(existingAccount);
    }

    @Override
    public void deleteAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));
        account.setDeleted(true);
        accountRepository.save(account);
    }

    @Override
    public void restoreAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        if (!account.isDeleted()) {
            throw new IllegalStateException("Account is already active.");
        }
        account.setDeleted(false);
        accountRepository.save(account);
    }
}
