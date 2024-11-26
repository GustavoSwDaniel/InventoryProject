package com.gustavoswdanioel.inventory_manager.application.usecase.products.impl;

import com.gustavoswdanioel.inventory_manager.application.dto.ProductDTO;
import com.gustavoswdanioel.inventory_manager.application.exception.ProductNotFoundException;
import com.gustavoswdanioel.inventory_manager.application.exception.UserNotFoundException;
import com.gustavoswdanioel.inventory_manager.application.mapper.ProductMapper;
import com.gustavoswdanioel.inventory_manager.application.usecase.products.UpdateProductUseCase;
import com.gustavoswdanioel.inventory_manager.domain.entity.AuditLogs;
import com.gustavoswdanioel.inventory_manager.domain.entity.Products;
import com.gustavoswdanioel.inventory_manager.domain.entity.Users;
import com.gustavoswdanioel.inventory_manager.domain.repository.AuditLogsRepository;
import com.gustavoswdanioel.inventory_manager.domain.repository.ProductRepository;
import com.gustavoswdanioel.inventory_manager.domain.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;

@Service
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AuditLogsRepository auditLogsRepository;
    private final UsersRepository usersRepository;

    private static final Logger logger = LoggerFactory.getLogger(UpdateProductUseCaseImpl.class);

    public UpdateProductUseCaseImpl(ProductRepository productRepository, ProductMapper productMapper,
                                    AuditLogsRepository auditLogsRepository,
                                    UsersRepository usersRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.auditLogsRepository = auditLogsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public ProductDTO execute(ProductDTO productDTO, String email) {
        logger.info("Updating product id: {}", productDTO.getId());
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Products product = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> {
                    logger.error("Product not found with id: {}", productDTO.getId());
                    return new ProductNotFoundException("Product not found");
                });

        String productBeforeUpdate = String.format("id=%d, name=%s, value=%f",
                product.getId(), product.getName(), product.getValue() / 100f);

        logger.info("Updating {} -> {}", product, productDTO);

        for (Field field : ProductDTO.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(productDTO);
                if (value != null) {
                    Field productField = Products.class.getDeclaredField(field.getName());
                    productField.setAccessible(true);
                    productField.set(product, value);
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                logger.error("Error accessing field: {}", e.getMessage());
            }
        }

        Products updatedProduct = productRepository.save(product);

        String productAfterUpdate = String.format("id=%d, name=%s, value=%f",
                updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getValue() / 100f);

        saveAuditLog(productBeforeUpdate, productAfterUpdate, "PRODUCT_UPDATED", user.getId());

        return productMapper.toDTO(updatedProduct);
    }

    private void saveAuditLog(String productBefore, String productAfter, String action, Long userId) {
        String data = String.format("Before: [%s], After: [%s]", productBefore, productAfter);

        AuditLogs auditLog = new AuditLogs();
        auditLog.setAction(action);
        auditLog.setData(data);
        auditLog.setUserId(userId);
        auditLogsRepository.save(auditLog);

        logger.info("Audit log saved for action: {}", action);
    }
}
