package com.gustavoswdanioel.inventory_manager.presentation.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Float value;
}
