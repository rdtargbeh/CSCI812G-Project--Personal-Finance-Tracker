package csci812_project.backend.repository;

import csci812_project.backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUser_UserId(Long userId);

    boolean existsByAccountNumber(String accountNumber);
}
