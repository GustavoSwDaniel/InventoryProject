package com.gustavoswdanioel.inventory_manager.application.mapper;

import com.gustavoswdanioel.inventory_manager.application.dto.AuditLogsDTO;
import com.gustavoswdanioel.inventory_manager.presentation.model.AuditLogsResponse;
import org.springframework.stereotype.Component;

@Component
public class AuditLogsResponseMapper {

    public AuditLogsResponse toResponse(AuditLogsDTO auditLogsDTO) {
        AuditLogsResponse auditLogsResponse = new AuditLogsResponse();
        auditLogsResponse.setId(auditLogsDTO.getId());
        auditLogsResponse.setAction(auditLogsDTO.getAction());
        auditLogsResponse.setData(auditLogsDTO.getData());
        auditLogsResponse.setUserId(auditLogsDTO.getUserId());
        auditLogsResponse.setName(auditLogsDTO.getUserName());
        auditLogsResponse.setCreatedAt(String.valueOf(auditLogsDTO.getCreatedAt()));
        return auditLogsResponse;
    }
}
