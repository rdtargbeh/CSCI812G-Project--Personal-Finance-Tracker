package csci812_project.backend.repository;

import csci812_project.backend.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUser_UserId(Long userId);
}
