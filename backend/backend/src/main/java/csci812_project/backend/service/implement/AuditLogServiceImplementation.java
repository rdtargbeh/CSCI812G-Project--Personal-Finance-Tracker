package csci812_project.backend.service.implement;

import csci812_project.backend.dto.AuditLogDTO;
import csci812_project.backend.entity.AuditLog;
import csci812_project.backend.entity.User;
import csci812_project.backend.mapper.AuditLogMapper;
import csci812_project.backend.repository.AuditLogRepository;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditLogServiceImplementation implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuditLogMapper auditLogMapper;

    @Override
    public void logAction(Long userId, String action, String entity, Long entityId, String oldValue, String newValue) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AuditLog auditLog = new AuditLog(); // ✅ Create an instance using `new`
        auditLog.setUser(user);
        auditLog.setAction(action);
        auditLog.setEntity(entity);
        auditLog.setEntityId(entityId);
        auditLog.setOldValue(oldValue);
        auditLog.setNewValue(newValue);
        auditLog.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(auditLog); // ✅ Save to the database
    }

    @Override
    public List<AuditLogDTO> getLogsByUser(Long userId) {
        return auditLogRepository.findByUser_UserId(userId)
                .stream()
                .map(auditLogMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditLogDTO> getLogsByEntity(String entity) {
        return auditLogRepository.findByEntity(entity)
                .stream()
                .map(auditLogMapper::toDTO)
                .collect(Collectors.toList());
    }
}
