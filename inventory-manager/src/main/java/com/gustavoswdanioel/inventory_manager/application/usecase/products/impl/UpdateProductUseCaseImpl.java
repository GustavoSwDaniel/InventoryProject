package com.gustavoswdanioel.inventory_manager.application.usecase.products.impl;

import com.gustavoswdanioel.inventory_manager.application.dto.ProductDTO;
import com.gustavoswdanioel.inventory_manager.application.exception.ProductNotFoundException;
import com.gustavoswdanioel.inventory_manager.application.mapper.ProductMapper;
import com.gustavoswdanioel.inventory_manager.application.usecase.products.UpdateProductUseCase;
import com.gustavoswdanioel.inventory_manager.domain.entity.Products;
import com.gustavoswdanioel.inventory_manager.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;


@Service
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public UpdateProductUseCaseImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional
    public ProductDTO execute(ProductDTO productDTO) {
        Products product = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

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
                System.out.print("standout");
            }
        }
        Products updatedProduct = productRepository.save(product);
        return productMapper.toDTO(updatedProduct);

    }
}
