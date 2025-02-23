package csci812_project.backend.repository;

import csci812_project.backend.entity.SavingsGoal;
import csci812_project.backend.enums.SavingsGoalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {

    /** ✅ Find all savings goals for a specific user */
    List<SavingsGoal> findByUser_UserId(Long userId);

    /** ✅ Find all active savings goals */
    List<SavingsGoal> findByStatus(SavingsGoalStatus status);

    /** ✅ Find all savings goals where `autoSave = true` */
    List<SavingsGoal> findByAutoSaveTrue();
}

