package csci812_project.backend.repository;

import csci812_project.backend.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByUser_UserId(Long userId);
}

