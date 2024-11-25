package com.gustavoswdanioel.inventory_manager.application.usecase.products.impl;

import com.gustavoswdanioel.inventory_manager.application.dto.ProductDTO;
import com.gustavoswdanioel.inventory_manager.application.mapper.ProductMapper;
import com.gustavoswdanioel.inventory_manager.application.usecase.products.CreateProductUseCase;
import com.gustavoswdanioel.inventory_manager.domain.entity.Products;
import com.gustavoswdanioel.inventory_manager.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateProductUseCaseImpl implements CreateProductUseCase {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public CreateProductUseCaseImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO execute(ProductDTO productDTO){
        Products product = productMapper.toEntity(productDTO);
        product.setValue(product.getValue() * 100);
        Products savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }
}
