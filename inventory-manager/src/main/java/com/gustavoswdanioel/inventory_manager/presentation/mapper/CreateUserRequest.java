package com.gustavoswdanioel.inventory_manager.presentation.mapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String name;
    private String email;
    private String password;
}
