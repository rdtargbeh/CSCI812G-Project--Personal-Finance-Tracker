package csci812_project.backend.repository;

import csci812_project.backend.entity.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LoanPaymentRepository extends JpaRepository<LoanPayment, Long> {

    List<LoanPayment> findByLoan_LoanId(Long loanId);

    List<LoanPayment> findByNextDueDate(LocalDate nextDueDate);
}

