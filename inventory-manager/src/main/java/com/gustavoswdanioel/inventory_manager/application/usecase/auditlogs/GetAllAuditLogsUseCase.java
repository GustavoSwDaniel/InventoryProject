package com.gustavoswdanioel.inventory_manager.application.usecase.auditlogs;

import com.gustavoswdanioel.inventory_manager.application.dto.AuditLogsDTO;
import org.springframework.data.domain.Page;

public interface GetAllAuditLogsUseCase {
    Page<AuditLogsDTO> execute(int page, int size, String email);
}
