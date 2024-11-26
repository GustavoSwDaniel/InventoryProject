package com.gustavoswdanioel.inventory_manager.presentation.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditLogsResponse {
    private Long id;
    private String action;
    private String data;
    private Long userId;
    private String name;
    private String createdAt;
}
