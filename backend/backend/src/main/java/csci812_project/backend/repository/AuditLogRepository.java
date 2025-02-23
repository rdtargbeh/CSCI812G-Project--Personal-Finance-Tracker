package csci812_project.backend.repository;

import csci812_project.backend.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    /** Find logs by user ID */
    List<AuditLog> findByUser_UserId(Long userId);

    /** Find logs by entity type (e.g., "Transaction", "Account") */
    List<AuditLog> findByEntity(String entity);
}
