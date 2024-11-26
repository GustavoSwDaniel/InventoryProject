package com.gustavoswdanioel.inventory_manager.domain.repository;

import com.gustavoswdanioel.inventory_manager.application.dto.AuditLogsDTO;
import com.gustavoswdanioel.inventory_manager.domain.entity.AuditLogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogsRepository extends JpaRepository<AuditLogs, Long> {

    @Query("SELECT new com.gustavoswdanioel.inventory_manager.application.dto.AuditLogsDTO(" +
            "al.id, al.action, al.data, al.createdAt, u.name, u.id) " +
            "FROM AuditLogs al JOIN Users u ON u.id = al.userId")
    Page<AuditLogsDTO> findAuditLogsWithUserName(Pageable pageable);
}
