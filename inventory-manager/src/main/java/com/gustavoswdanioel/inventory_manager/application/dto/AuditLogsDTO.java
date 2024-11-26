package com.gustavoswdanioel.inventory_manager.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogsDTO {
    private Long id;
    private String action;
    private String data;
    private LocalDateTime createdAt;
    private String userName;
    private Long userId;

}
