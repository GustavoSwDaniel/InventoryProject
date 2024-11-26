package com.gustavoswdanioel.inventory_manager.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "audit_log")
public class AuditLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String action;

    @Column(columnDefinition = "TEXT")
    private String data;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    // toString for debugging
    @Override
    public String toString() {
        return "AuditLogs{" +
                "id=" + id +
                ", action='" + action + '\'' +
                ", data='" + data + '\'' +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                '}';
    }
}
