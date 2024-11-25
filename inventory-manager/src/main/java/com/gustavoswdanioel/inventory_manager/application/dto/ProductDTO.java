package com.gustavoswdanioel.inventory_manager.application.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Long value;
}
