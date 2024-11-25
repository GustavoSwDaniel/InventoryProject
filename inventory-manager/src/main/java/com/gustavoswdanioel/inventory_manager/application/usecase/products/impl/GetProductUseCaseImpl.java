package com.gustavoswdanioel.inventory_manager.application.usecase.products.impl;

import com.gustavoswdanioel.inventory_manager.application.dto.ProductDTO;
import com.gustavoswdanioel.inventory_manager.application.exception.ProductNotFoundException;
import com.gustavoswdanioel.inventory_manager.application.mapper.ProductMapper;
import com.gustavoswdanioel.inventory_manager.application.usecase.products.GetProductUseCase;
import com.gustavoswdanioel.inventory_manager.domain.entity.Products;
import com.gustavoswdanioel.inventory_manager.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class GetProductUseCaseImpl implements GetProductUseCase {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public GetProductUseCaseImpl(ProductRepository productRepository, ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO execute(Long id){
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return productMapper.toDTO((product));
    }
}
