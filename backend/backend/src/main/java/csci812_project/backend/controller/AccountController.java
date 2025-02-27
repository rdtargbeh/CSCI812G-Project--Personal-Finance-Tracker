package csci812_project.backend.controller;

import csci812_project.backend.dto.AccountDTO;
import csci812_project.backend.entity.Account;
import csci812_project.backend.mapper.AccountMapper;
import csci812_project.backend.repository.AccountRepository;
import csci812_project.backend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private  AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountMapper accountMapper;

    /**
     * Creates a new account for a user.
     */
    @PostMapping("/create/{userId}")
    public ResponseEntity<AccountDTO> createAccount(@PathVariable Long userId, @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.createAccount(userId, accountDTO));
    }

    /**
     * Retrieves account details by account ID.
     */
    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);

        if (account.isPresent()) {
            return ResponseEntity.ok(accountMapper.toDTO(account.get())); // ✅ 200 OK if found
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account Not Found"); // ✅ 404 Response
        }
    }


    /**
     * Retrieves all accounts for a specific user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountDTO>> getAccountsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getAccountsByUser(userId));
    }

    /**
     * Updates an existing account.
     */
    @PutMapping("/{accountId}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long accountId, @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.updateAccount(accountId, accountDTO));
    }

    /**
     * Soft deletes an account.
     */
    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.ok("Account has been soft deleted.");
    }

    /**
     * Restores a previously soft-deleted account.
     */
    @PutMapping("/{accountId}/restore")
    public ResponseEntity<String> restoreAccount(@PathVariable Long accountId) {
        accountService.restoreAccount(accountId);
        return ResponseEntity.ok("Account has been restored.");
    }
}
