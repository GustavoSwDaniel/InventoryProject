package com.gustavoswdanioel.inventory_manager.application.mapper;

import com.gustavoswdanioel.inventory_manager.application.dto.LoginAuthenticatedDTO;
import org.springframework.stereotype.Component;

@Component
public class LoginAuthenticatedMapper {

    public LoginAuthenticatedDTO toDTO(String token){
        LoginAuthenticatedDTO loginAuthenticatedDTO = new LoginAuthenticatedDTO();
        loginAuthenticatedDTO.setToken(token);
        return loginAuthenticatedDTO;
    }
}
