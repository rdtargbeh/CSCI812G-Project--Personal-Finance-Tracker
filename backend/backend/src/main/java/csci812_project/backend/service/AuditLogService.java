package csci812_project.backend.service;

import csci812_project.backend.dto.AuditLogDTO;

import java.util.List;

public interface AuditLogService {

    void logAction(Long userId, String action, String entity, Long entityId, String oldValue, String newValue);

    List<AuditLogDTO> getLogsByUser(Long userId);

    List<AuditLogDTO> getLogsByEntity(String entity);
}
