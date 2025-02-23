package csci812_project.backend.mapper;

import csci812_project.backend.dto.AuditLogDTO;
import csci812_project.backend.entity.AuditLog;
import csci812_project.backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuditLogMapper {

    public AuditLogDTO toDTO(AuditLog auditLog) {
        if (auditLog == null) return null;

        AuditLogDTO dto = new AuditLogDTO();
        dto.setLogId(auditLog.getLogId());
        dto.setUserId(auditLog.getUser().getUserId());
        dto.setAction(auditLog.getAction()); // ✅ Corrected method (changed `setAction()` to `getAction()`)
        dto.setEntity(auditLog.getEntity());
        dto.setEntityId(auditLog.getEntityId());
        dto.setOldValue(auditLog.getOldValue());
        dto.setNewValue(auditLog.getNewValue());
        dto.setTimestamp(auditLog.getTimestamp());
        return dto;
    }


    public AuditLog toEntity(AuditLogDTO auditLogDTO, User user) {
        AuditLog auditLog = new AuditLog();
        auditLog.setUser(user);  // ✅ Pass user object to correctly link the entity
        auditLog.setAction(auditLogDTO.getAction());
        auditLog.setEntity(auditLogDTO.getEntity());
        auditLog.setEntityId(auditLogDTO.getEntityId());
        auditLog.setOldValue(auditLogDTO.getOldValue());
        auditLog.setNewValue(auditLogDTO.getNewValue());
        auditLog.setTimestamp(auditLogDTO.getTimestamp());
        return auditLog;
    }

}

