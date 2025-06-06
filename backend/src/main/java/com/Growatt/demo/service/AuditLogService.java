package com.Growatt.demo.service;

import com.Growatt.demo.model.AuditLog;
import com.Growatt.demo.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogService {

    private final AuditLogRepository repository;

    public AuditLogService(AuditLogRepository repository) {
        this.repository = repository;
    }

    public void log(String endpoint, String method, String clientIp) {
        AuditLog log = new AuditLog(endpoint, method, clientIp, LocalDateTime.now());
        repository.save(log);
    }
}