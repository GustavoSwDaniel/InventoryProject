package com.gustavoswdanioel.inventory_manager.application.usecase.products;

import com.gustavoswdanioel.inventory_manager.application.dto.ProductDTO;

public interface CreateProductUseCase {
    ProductDTO execute(ProductDTO product, String email);
}
