package com.gustavoswdanioel.inventory_manager.application.usecase.products.impl;

import com.gustavoswdanioel.inventory_manager.application.exception.UserNotFoundException;
import com.gustavoswdanioel.inventory_manager.application.usecase.products.DeleteProductUseCase;
import com.gustavoswdanioel.inventory_manager.domain.entity.AuditLogs;
import com.gustavoswdanioel.inventory_manager.domain.entity.Users;
import com.gustavoswdanioel.inventory_manager.domain.repository.AuditLogsRepository;
import com.gustavoswdanioel.inventory_manager.domain.repository.ProductRepository;
import com.gustavoswdanioel.inventory_manager.domain.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeleteProductUseCaseImpl implements DeleteProductUseCase {
    private final ProductRepository productRepository;
    private final AuditLogsRepository auditLogsRepository;
    private final UsersRepository usersRepository;

    private static final Logger logger = LoggerFactory.getLogger(DeleteProductUseCaseImpl.class);

    public DeleteProductUseCaseImpl(ProductRepository productRepository,
                                    AuditLogsRepository auditLogsRepository,
                                    UsersRepository usersRepository) {
        this.productRepository = productRepository;
        this.auditLogsRepository = auditLogsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public void execute(Long id, String email) {
        logger.info("Deleting product with ID: {}", id);

        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", email);
                    return new UserNotFoundException("User not found");
                });

        productRepository.softDelete(id);
        saveAuditLog(user, id);
        logger.info("Audit log saved for product deletion");
    }

    private void saveAuditLog(Users user, Long productId) {
        AuditLogs auditLog = new AuditLogs();
        auditLog.setAction("DELETE_PRODUCT");
        auditLog.setData(String.format("Product deleted with ID: %d", productId));
        auditLog.setUserId(user.getId());
        auditLogsRepository.save(auditLog);
        logger.info("Audit log saved for product deletion with ID: {}", productId);
    }
}
