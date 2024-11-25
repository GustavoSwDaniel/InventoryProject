package com.gustavoswdanioel.inventory_manager.application.usecase.products.impl;

import com.gustavoswdanioel.inventory_manager.application.usecase.products.DeleteProductUseCase;
import com.gustavoswdanioel.inventory_manager.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteProductUseCaseImpl implements DeleteProductUseCase {
    private final ProductRepository productRepository;

    public DeleteProductUseCaseImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public void execute(Long id) {
        productRepository.softDelete(id);
    }
}
