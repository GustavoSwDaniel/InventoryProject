package com.gustavoswdanioel.inventory_manager.application.mapper;

import com.gustavoswdanioel.inventory_manager.application.dto.AuditLogsDTO;
import com.gustavoswdanioel.inventory_manager.domain.entity.AuditLogs;
import org.springframework.stereotype.Component;

@Component
public class AuditLogsMapper {

    public AuditLogsDTO toDTO(AuditLogs auditLogs) {
        AuditLogsDTO auditLogsDTO = new AuditLogsDTO();
        auditLogsDTO.setId(auditLogs.getId());
        auditLogsDTO.setAction(auditLogs.getAction());
        auditLogsDTO.setData(auditLogs.getData());
        auditLogsDTO.setUserId(auditLogs.getUserId());
        auditLogsDTO.setCreatedAt(auditLogs.getCreatedAt());
        return auditLogsDTO;
    }

    public AuditLogs toEntity(AuditLogsDTO auditLogsDTO) {
        AuditLogs auditLogs = new AuditLogs();
        auditLogs.setAction(auditLogsDTO.getAction());
        auditLogs.setData(auditLogsDTO.getData());
        auditLogs.setUserId(auditLogsDTO.getUserId());
        auditLogs.setCreatedAt(auditLogsDTO.getCreatedAt());
        return auditLogs;
    }
}
