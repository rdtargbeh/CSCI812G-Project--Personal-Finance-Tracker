package csci812_project.backend.repository;

import csci812_project.backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUser_UserId(Long userId);

    boolean existsByAccountNumber(String accountNumber);

    Optional<Account> findByAccountIdAndIsDeletedFalse(Long accountId); // ✅ Exclude deleted accounts
    List<Account> findByUser_UserIdAndIsDeletedFalse(Long userId); // ✅ Exclude deleted accounts
}
