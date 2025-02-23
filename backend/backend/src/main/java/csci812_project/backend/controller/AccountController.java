package csci812_project.backend.controller;

import csci812_project.backend.dto.AccountDTO;
import csci812_project.backend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")

public class AccountController {

    @Autowired
    private  AccountService accountService;

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
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
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
