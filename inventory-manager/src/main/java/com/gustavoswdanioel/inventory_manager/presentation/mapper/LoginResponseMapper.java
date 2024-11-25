package com.gustavoswdanioel.inventory_manager.presentation.mapper;

import com.gustavoswdanioel.inventory_manager.application.dto.LoginAuthenticatedDTO;
import com.gustavoswdanioel.inventory_manager.presentation.model.LoginResponse;
import org.springframework.stereotype.Component;

@Component
public class LoginResponseMapper {
    public LoginResponse toResponse(LoginAuthenticatedDTO loginAuthenticatedDTO){
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(loginAuthenticatedDTO.getToken());
        return loginResponse;
    }
}
