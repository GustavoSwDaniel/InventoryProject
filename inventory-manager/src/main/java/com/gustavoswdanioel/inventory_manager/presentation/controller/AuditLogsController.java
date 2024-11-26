package com.gustavoswdanioel.inventory_manager.presentation.controller;

import com.gustavoswdanioel.inventory_manager.application.dto.AuditLogsDTO;
import com.gustavoswdanioel.inventory_manager.application.mapper.AuditLogsResponseMapper;
import com.gustavoswdanioel.inventory_manager.application.usecase.auditlogs.GetAllAuditLogsUseCase;
import com.gustavoswdanioel.inventory_manager.presentation.model.AuditLogsResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/logs")
public class AuditLogsController {
    private final AuditLogsResponseMapper auditLogsResponseMapper;
    private final GetAllAuditLogsUseCase getAllAuditLogsUseCase;

    public AuditLogsController(AuditLogsResponseMapper auditLogsResponseMapper, GetAllAuditLogsUseCase getAllAuditLogsUseCase) {
        this.auditLogsResponseMapper = auditLogsResponseMapper;
        this.getAllAuditLogsUseCase = getAllAuditLogsUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<AuditLogsResponse>> getAllLogs(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "12") int size){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Page<AuditLogsDTO> auditLogsDTOS = getAllAuditLogsUseCase.execute(page, size, userEmail);
        Page<AuditLogsResponse> auditLogsResponses = auditLogsDTOS.map(auditLogsResponseMapper::toResponse);
        return new ResponseEntity<>(auditLogsResponses, HttpStatus.OK);
    }
}
