package com.gustavoswdanioel.inventory_manager.presentation.mapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {
    private String name;
    private String description;
    private Long value;
}