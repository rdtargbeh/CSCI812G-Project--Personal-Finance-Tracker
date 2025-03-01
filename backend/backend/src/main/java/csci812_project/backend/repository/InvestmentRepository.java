package csci812_project.backend.repository;

import csci812_project.backend.entity.Investment;
import csci812_project.backend.enums.InvestmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    List<Investment> findByUser_UserId(Long userId);

    List<Investment> findByIsDeletedFalse();

    List<Investment> findByInvestmentType(InvestmentType investmentType);
}

