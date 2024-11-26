package com.gustavoswdanioel.inventory_manager.application.usecase.products.impl;

import com.gustavoswdanioel.inventory_manager.application.dto.ProductDTO;
import com.gustavoswdanioel.inventory_manager.application.exception.UserNotFoundException;
import com.gustavoswdanioel.inventory_manager.application.mapper.ProductMapper;
import com.gustavoswdanioel.inventory_manager.application.usecase.products.CreateProductUseCase;
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
public class CreateProductUseCaseImpl implements CreateProductUseCase {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AuditLogsRepository auditLogsRepository;
    private final UsersRepository usersRepository;

    private static final Logger logger = LoggerFactory.getLogger(CreateProductUseCaseImpl.class);

    public CreateProductUseCaseImpl(ProductRepository productRepository,
                                    ProductMapper productMapper,
                                    AuditLogsRepository auditLogsRepository,
                                    UsersRepository usersRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.auditLogsRepository = auditLogsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public ProductDTO execute(ProductDTO productDTO, String email) {
        logger.info("Creating product: {}", productDTO.getName());
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", email);
                    return new UserNotFoundException("User not found");
                });

        Products product = productMapper.toEntity(productDTO);
        product.setValue(product.getValue() * 100);
        logger.info("Converted value to cents: {} -> {}", productDTO.getValue(), product.getValue());

        Products savedProduct = productRepository.save(product);
        logger.info("Product created with ID: {}", savedProduct.getId());

        saveAuditLog(user, productDTO.getName());

        logger.info("Audit log saved for product creation");

        return productMapper.toDTO(savedProduct);
    }

    private void saveAuditLog(Users user, String productName) {
        AuditLogs auditLog = new AuditLogs();
        auditLog.setAction("CREATE_PRODUCT");
        auditLog.setData(String.format("Product created: %s", productName));
        auditLog.setUserId(user.getId());
        auditLogsRepository.save(auditLog);
        logger.info("Audit log saved for product creation: {}", productName);
    }
}
