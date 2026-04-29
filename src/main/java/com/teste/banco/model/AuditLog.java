package com.teste.banco.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action; // e.g., LOGIN, CREATE, UPDATE, DELETE

    private String entityName; // e.g., Cliente, Conta

    private String entityId; // ID of the entity

    private String userLogin; // who performed the action

    private LocalDateTime timestamp;

    private String details; // additional info, like old/new values

    private String ipAddress;

    private String userAgent;
}
