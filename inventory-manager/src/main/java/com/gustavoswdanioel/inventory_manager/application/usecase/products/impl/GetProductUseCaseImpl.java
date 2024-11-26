package com.gustavoswdanioel.inventory_manager.application.usecase.products.impl;

import com.gustavoswdanioel.inventory_manager.application.dto.ProductDTO;
import com.gustavoswdanioel.inventory_manager.application.exception.ProductNotFoundException;
import com.gustavoswdanioel.inventory_manager.application.exception.UserNotFoundException;
import com.gustavoswdanioel.inventory_manager.application.usecase.products.GetProductUseCase;
import com.gustavoswdanioel.inventory_manager.application.mapper.ProductMapper;
import com.gustavoswdanioel.inventory_manager.domain.entity.AuditLogs;
import com.gustavoswdanioel.inventory_manager.domain.entity.Products;
import com.gustavoswdanioel.inventory_manager.domain.entity.Users;
import com.gustavoswdanioel.inventory_manager.domain.repository.AuditLogsRepository;
import com.gustavoswdanioel.inventory_manager.domain.repository.ProductRepository;
import com.gustavoswdanioel.inventory_manager.domain.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GetProductUseCaseImpl implements GetProductUseCase {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AuditLogsRepository auditLogsRepository;
    private final UsersRepository usersRepository;

    private static final Logger logger = LoggerFactory.getLogger(GetProductUseCaseImpl.class);

    public GetProductUseCaseImpl(ProductRepository productRepository, ProductMapper productMapper,
                                 AuditLogsRepository auditLogsRepository,
                                 UsersRepository usersRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.auditLogsRepository = auditLogsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public ProductDTO execute(Long id, String email) {
        logger.info("Getting product id {}", id);

        // Buscar usuário pelo e-mail
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", email);
                    return new UserNotFoundException("User not found");
                });

        Products product = productRepository.findById(id)
                .orElseThrow(() -> {
                    saveAuditLog(null, "GET_PRODUCT_NOT_FOUND", user.getId(), id);
                    logger.info("Product not found with id: {}", id);
                    return new ProductNotFoundException("Product not found");
                });

        saveAuditLog(product, "GET_PRODUCT", user.getId(), null);

        return productMapper.toDTO(product);
    }

    private void saveAuditLog(Products product, String action, Long userId, Long productId) {
        AuditLogs auditLog = new AuditLogs();
        auditLog.setAction(action);

        if (product != null) {
            auditLog.setData(String.format("Product id=%d, name=%s, value=%f", product.getId(), product.getName(),
                    product.getValue() / 100f));
        } else if (productId != null) {
            auditLog.setData(String.format("Product not found: id=%d", productId));
        }

        auditLog.setUserId(userId);
        auditLogsRepository.save(auditLog);
        logger.info("Audit log saved for action: {}", action);
    }
}
