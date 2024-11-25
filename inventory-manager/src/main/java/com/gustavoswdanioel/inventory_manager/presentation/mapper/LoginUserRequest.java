package com.gustavoswdanioel.inventory_manager.presentation.mapper;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginUserRequest {
    private String email;
    private String password;
}
