package csci812_project.backend.controller;

import csci812_project.backend.dto.AuditLogDTO;
import csci812_project.backend.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    /**
     * Retrieves audit logs for a specific user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLogDTO>> getLogsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(auditLogService.getLogsByUser(userId));
    }

    /**
     * Retrieves audit logs for a specific entity type.
     */
    @GetMapping("/entity/{entity}")
    public ResponseEntity<List<AuditLogDTO>> getLogsByEntity(@PathVariable String entity) {
        return ResponseEntity.ok(auditLogService.getLogsByEntity(entity));
    }

    /**
     * ✅ Manually logs an action for auditing purposes.
     */
    @PostMapping("/log")
    public ResponseEntity<String> logAction(
            @RequestParam Long userId,
            @RequestParam String action,
            @RequestParam String entity,
            @RequestParam Long entityId,
            @RequestParam(required = false) String oldValue,
            @RequestParam(required = false) String newValue) {

        auditLogService.logAction(userId, action, entity, entityId, oldValue, newValue);
        return ResponseEntity.ok("Action logged successfully.");
    }
}

