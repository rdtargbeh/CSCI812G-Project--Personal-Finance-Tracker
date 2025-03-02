package csci812_project.backend.repository;

import csci812_project.backend.entity.Loan;
import csci812_project.backend.entity.User;
import csci812_project.backend.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUser_UserId(Long userId);

    List<Loan> findByUserAndStatus(User user, LoanStatus status);

    // ✅ Find loans by status (ACTIVE, PAID_OFF, DEFAULTED)
    List<Loan> findByStatus(LoanStatus status);

    // ✅ Count the number of loans a user has
    long countByUser_UserId(Long userId);

    // ✅ Sum all outstanding balances for a user's loans
    @Query("SELECT COALESCE(SUM(l.outstandingBalance), 0) FROM Loan l WHERE l.user.userId = :userId")
    Optional<BigDecimal> getTotalDebtByUser(@Param("userId") Long userId);

    // ✅ Sum of all loan amounts borrowed by user
    @Query("SELECT COALESCE(SUM(l.amountBorrowed), 0) FROM Loan l WHERE l.user.userId = :userId")
    Optional<BigDecimal> getTotalLoanBorrowedByUser(@Param("userId") Long userId);

    @Query("SELECT COALESCE(SUM(l.amountBorrowed), 0) FROM Loan l WHERE l.user.userId = :userId")
    BigDecimal getTotalLoanAmountByUserId(@Param("userId") Long userId);

    @Query("SELECT COALESCE(SUM(l.outstandingBalance), 0) FROM Loan l WHERE l.user.userId = :userId")
    BigDecimal getOutstandingLoanBalanceByUserId(@Param("userId") Long userId);

}
