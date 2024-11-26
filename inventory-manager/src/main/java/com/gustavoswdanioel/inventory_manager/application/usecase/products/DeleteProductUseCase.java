package com.gustavoswdanioel.inventory_manager.application.usecase.products;

import com.gustavoswdanioel.inventory_manager.application.dto.ProductDTO;

public interface DeleteProductUseCase {
    void execute(Long id, String email);
}
