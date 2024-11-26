package com.gustavoswdanioel.inventory_manager.application.usecase.products.impl;

import com.gustavoswdanioel.inventory_manager.application.dto.ProductDTO;
import com.gustavoswdanioel.inventory_manager.application.exception.UserNotFoundException;
import com.gustavoswdanioel.inventory_manager.application.mapper.ProductMapper;
import com.gustavoswdanioel.inventory_manager.application.usecase.products.GetAllProductsUseCase;
import com.gustavoswdanioel.inventory_manager.domain.entity.AuditLogs;
import com.gustavoswdanioel.inventory_manager.domain.entity.Products;
import com.gustavoswdanioel.inventory_manager.domain.entity.Users;
import com.gustavoswdanioel.inventory_manager.domain.repository.AuditLogsRepository;
import com.gustavoswdanioel.inventory_manager.domain.repository.ProductRepository;
import com.gustavoswdanioel.inventory_manager.domain.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetAllProductUseCaseImpl implements GetAllProductsUseCase {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AuditLogsRepository auditLogsRepository;
    private final UsersRepository usersRepository;

    private static final Logger logger = LoggerFactory.getLogger(GetAllProductUseCaseImpl.class);

    public GetAllProductUseCaseImpl(ProductRepository productRepository, ProductMapper productMapper,
                                    AuditLogsRepository auditLogsRepository,
                                    UsersRepository usersRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.auditLogsRepository = auditLogsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Page<ProductDTO> execute(int page, int size, String email) {
        logger.info("Get paginated all products: page: {} size: {}", page, size);

        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", email);
                    return new UserNotFoundException("User not found");
                });

        Pageable pageable = PageRequest.of(page, size);
        Page<Products> products = productRepository.findByActiveTrue(pageable);

        saveAuditLog(user, page, size);

        logger.info("Audit log saved for fetching all products");
        return products.map(productMapper::toDTO);
    }

    private void saveAuditLog(Users user, int page, int size) {
        AuditLogs auditLog = new AuditLogs();
        auditLog.setAction("GET_ALL_PRODUCTS");
        auditLog.setData(String.format("Fetched products: page=%d, size=%d", page, size));
        auditLog.setUserId(user.getId());
        auditLogsRepository.save(auditLog);
        logger.info("Audit log saved for action: {}", "GET_ALL_PRODUCTS");
    }
}
