package com.gustavoswdanioel.inventory_manager.application.usecase.products.impl;

import com.gustavoswdanioel.inventory_manager.application.dto.ProductDTO;
import com.gustavoswdanioel.inventory_manager.application.mapper.ProductMapper;
import com.gustavoswdanioel.inventory_manager.application.usecase.products.GetAllProductsUseCase;
import com.gustavoswdanioel.inventory_manager.domain.entity.Products;
import com.gustavoswdanioel.inventory_manager.domain.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetAllProductUseCaseImpl implements GetAllProductsUseCase {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public GetAllProductUseCaseImpl(ProductRepository productRepository, ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Page<ProductDTO> execute(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Products> products = productRepository.findByActiveTrue(pageable);
        return products.map(productMapper::toDTO);
    }
}
