package com.gustavoswdanioel.inventory_manager.application.mapper;

import com.gustavoswdanioel.inventory_manager.application.dto.ProductDTO;
import com.gustavoswdanioel.inventory_manager.domain.entity.Products;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Products toEntity(ProductDTO productDTO) {
        Products product = new Products();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setValue(productDTO.getValue());
        return product;
    }

    public ProductDTO toDTO(Products product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setValue(product.getValue());
        return productDTO;
    }
}
