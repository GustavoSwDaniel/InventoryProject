package com.gustavoswdanioel.inventory_manager.application.usecase.products;

import com.gustavoswdanioel.inventory_manager.application.dto.ProductDTO;
import org.springframework.data.domain.Page;

public interface GetAllProductsUseCase {
    Page<ProductDTO> execute(int page, int size);

}
