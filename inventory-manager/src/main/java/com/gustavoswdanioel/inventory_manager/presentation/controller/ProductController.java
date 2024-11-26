package com.gustavoswdanioel.inventory_manager.presentation.controller;


import com.gustavoswdanioel.inventory_manager.application.dto.ProductDTO;
import com.gustavoswdanioel.inventory_manager.application.usecase.products.*;
import com.gustavoswdanioel.inventory_manager.presentation.mapper.CreateProductRequest;
import com.gustavoswdanioel.inventory_manager.presentation.mapper.ProductResponseMapper;
import com.gustavoswdanioel.inventory_manager.presentation.model.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final ProductResponseMapper productResponseMapper;
    private final DeleteProductUseCase deleteProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;


    public ProductController(CreateProductUseCase createProductUseCase, ProductResponseMapper productResponseMapper,
                             GetProductUseCase getProductUseCase, GetAllProductsUseCase getAllProductsUseCase,
                             DeleteProductUseCase deleteProductUseCase, UpdateProductUseCase updateProductUseCase){
        this.createProductUseCase = createProductUseCase;
        this.productResponseMapper = productResponseMapper;
        this.getProductUseCase = getProductUseCase;
        this.getAllProductsUseCase = getAllProductsUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest createProductRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(createProductRequest.getName());
        productDTO.setDescription(createProductRequest.getDescription());
        productDTO.setValue(createProductRequest.getValue());


        ProductDTO createdProductDTO = createProductUseCase.execute(productDTO, userEmail);
        ProductResponse productResponse = productResponseMapper.toResponse(createdProductDTO);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        ProductDTO productDTO = getProductUseCase.execute(id, userEmail);
        ProductResponse productResponse = productResponseMapper.toResponse(productDTO);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "12") int size){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Page<ProductDTO> products = getAllProductsUseCase.execute(page, size, userEmail);
        Page<ProductResponse> productResponses = products.map(productResponseMapper::toResponse);
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProducts(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        deleteProductUseCase.execute(id, userEmail);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ProductResponse> updateProducts(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        productDTO.setId(id);
        if(productDTO.getValue() != null)
            productDTO.setValue(productDTO.getValue() * 100);
        ProductDTO updatedProductDTO = updateProductUseCase.execute(productDTO, userEmail);
        ProductResponse productResponse = productResponseMapper.toResponse(updatedProductDTO);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

}
