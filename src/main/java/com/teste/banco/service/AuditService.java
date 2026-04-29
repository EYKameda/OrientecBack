package com.teste.banco.service;

import com.teste.banco.model.AuditLog;
import com.teste.banco.repository.AuditLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public void logAction(String action, String entityName, String entityId, String userLogin, String details, HttpServletRequest request) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setEntityName(entityName);
        log.setEntityId(entityId);
        log.setUserLogin(userLogin);
        log.setTimestamp(LocalDateTime.now());
        log.setDetails(details);
        if (request != null) {
            log.setIpAddress(request.getRemoteAddr());
            log.setUserAgent(request.getHeader("User-Agent"));
        }
        auditLogRepository.save(log);
    }

    public void logLogin(String userLogin, HttpServletRequest request) {
        logAction("LOGIN", "Funcionario", null, userLogin, "User logged in", request);
    }

    public void logDataChange(String action, String entityName, String entityId, String userLogin, String details, HttpServletRequest request) {
        logAction(action, entityName, entityId, userLogin, details, request);
    }
}
