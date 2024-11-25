package com.gustavoswdanioel.inventory_manager.presentation.mapper;

import com.gustavoswdanioel.inventory_manager.application.dto.ProductDTO;
import com.gustavoswdanioel.inventory_manager.presentation.model.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseMapper {
    public ProductResponse toResponse(ProductDTO productDTO) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(productDTO.getId());
        productResponse.setName(productDTO.getName());
        productResponse.setDescription(productDTO.getDescription());
        productResponse.setValue(productDTO.getValue() / 100.0f);
        return productResponse;
    }
}
