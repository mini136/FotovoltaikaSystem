package com.Growatt.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String endpoint;

    private String method;

    private String clientIp;

    private LocalDateTime timestamp;

    public AuditLog() {
    }

    public AuditLog(String endpoint, String method, String clientIp, LocalDateTime timestamp) {
        this.endpoint = endpoint;
        this.method = method;
        this.clientIp = clientIp;
        this.timestamp = timestamp;
    }

    // Gettery a settery...
}
