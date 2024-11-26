package com.gustavoswdanioel.inventory_manager.application.usecase.auditlogs.impl;

import com.gustavoswdanioel.inventory_manager.application.dto.AuditLogsDTO;
import com.gustavoswdanioel.inventory_manager.application.exception.UserNotFoundException;
import com.gustavoswdanioel.inventory_manager.application.mapper.AuditLogsMapper;
import com.gustavoswdanioel.inventory_manager.application.usecase.auditlogs.GetAllAuditLogsUseCase;
import com.gustavoswdanioel.inventory_manager.domain.entity.AuditLogs;
import com.gustavoswdanioel.inventory_manager.domain.entity.Users;
import com.gustavoswdanioel.inventory_manager.domain.repository.AuditLogsRepository;
import com.gustavoswdanioel.inventory_manager.domain.repository.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetAllAuditLogsUseCaseImpl implements GetAllAuditLogsUseCase {
    private final AuditLogsRepository auditLogsRepository;
    private final UsersRepository usersRepository;
    private final AuditLogsMapper auditLogsMapper;

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GetAllAuditLogsUseCaseImpl.class);

    public GetAllAuditLogsUseCaseImpl(AuditLogsRepository auditLogsRepository,
                                      UsersRepository usersRepository,
                                      AuditLogsMapper auditLogsMapper) {
        this.auditLogsRepository = auditLogsRepository;
        this.usersRepository = usersRepository;
        this.auditLogsMapper = auditLogsMapper;
    }

    @Override
    public Page<AuditLogsDTO> execute(int page, int size, String email) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", email);
                    return new UserNotFoundException("User not found");
                });

        Pageable pageable = PageRequest.of(page, size);
        Page<AuditLogsDTO> auditLogs = auditLogsRepository.findAuditLogsWithUserName(pageable);
        saveAuditLog(user.getId());
        return auditLogs;
    }

    private void saveAuditLog(Long id) {
        AuditLogs auditLogs = new AuditLogs();
        auditLogs.setAction("GET_ALL_AUDIT_LOGS");
        auditLogs.setData("Getting audit logs");
        auditLogs.setUserId(id);
        auditLogsRepository.save(auditLogs);
        logger.info("Audit log saved for getting audit logs");
    }
}
