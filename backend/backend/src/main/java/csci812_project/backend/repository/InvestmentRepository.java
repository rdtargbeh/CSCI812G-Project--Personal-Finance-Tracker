package csci812_project.backend.repository;

import csci812_project.backend.entity.Investment;
import csci812_project.backend.enums.InvestmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    List<Investment> findByUser_UserId(Long userId);

    List<Investment> findByIsDeletedFalse();

    List<Investment> findByInvestmentType(InvestmentType investmentType);

    @Query("SELECT COALESCE(SUM(i.investedAmount), 0) FROM Investment i WHERE i.user.userId = :userId")
    BigDecimal getTotalInvestmentsByUserId(@Param("userId") Long userId);
}

