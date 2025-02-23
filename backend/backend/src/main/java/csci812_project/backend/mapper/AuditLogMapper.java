package csci812_project.backend.mapper;

import csci812_project.backend.dto.AuditLogDTO;
import csci812_project.backend.entity.AuditLog;
import org.springframework.stereotype.Component;

@Component
public class AuditLogMapper {

    public AuditLogDTO toDTO(AuditLog auditLog) {
        return AuditLogDTO.builder()
                .logId(auditLog.getLogId())
                .userId(auditLog.getUser().getUserId())
                .action(auditLog.getAction())
                .entity(auditLog.getEntity())
                .entityId(auditLog.getEntityId())
                .oldValue(auditLog.getOldValue())
                .newValue(auditLog.getNewValue())
                .timestamp(auditLog.getTimestamp())
                .build();
    }

    public AuditLog toEntity(AuditLogDTO auditLogDTO) {
        return AuditLog.builder()
                .action(auditLogDTO.getAction())
                .entity(auditLogDTO.getEntity())
                .entityId(auditLogDTO.getEntityId())
                .oldValue(auditLogDTO.getOldValue())
                .newValue(auditLogDTO.getNewValue())
                .timestamp(auditLogDTO.getTimestamp())
                .build();
    }
}

