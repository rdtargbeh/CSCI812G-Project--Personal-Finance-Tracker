package csci812_project.backend.service.implement;

import csci812_project.backend.dto.AuditLogDTO;
import csci812_project.backend.entity.AuditLog;
import csci812_project.backend.entity.User;
import csci812_project.backend.mapper.AuditLogMapper;
import csci812_project.backend.repository.AuditLogRepository;
import csci812_project.backend.repository.UserRepository;
import csci812_project.backend.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImplementation implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;
    private final AuditLogMapper auditLogMapper;

    @Override
    public void logAction(Long userId, String action, String entity, Long entityId, String oldValue, String newValue) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AuditLog auditLog = AuditLog.builder()
                .user(user)
                .action(action)
                .entity(entity)
                .entityId(entityId)
                .oldValue(oldValue)
                .newValue(newValue)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(auditLog);
    }

    @Override
    public List<AuditLogDTO> getLogsByUser(Long userId) {
        return auditLogRepository.findByUserId(userId)
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
