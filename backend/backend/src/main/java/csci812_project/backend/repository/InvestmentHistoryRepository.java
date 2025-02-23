package csci812_project.backend.repository;

import csci812_project.backend.entity.InvestmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface InvestmentHistoryRepository extends JpaRepository<InvestmentHistory, Long> {

    // ✅ Fetch all investment history for a given investment
    List<InvestmentHistory> findByInvestment_InvestmentId(Long investmentId);

    // ✅ Fetch history within a specific date range
    @Query("SELECT ih FROM InvestmentHistory ih WHERE ih.investment.investmentId = :investmentId " +
            "AND ih.recordedAt BETWEEN :startDate AND :endDate")
    List<InvestmentHistory> findByInvestmentAndDateRange(
            @Param("investmentId") Long investmentId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
