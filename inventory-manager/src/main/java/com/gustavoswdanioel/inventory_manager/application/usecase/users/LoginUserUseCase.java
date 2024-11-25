package com.gustavoswdanioel.inventory_manager.application.usecase.users;

import com.gustavoswdanioel.inventory_manager.application.dto.LoginAuthenticatedDTO;
import com.gustavoswdanioel.inventory_manager.application.dto.LoginDTO;

public interface LoginUserUseCase {
    public LoginAuthenticatedDTO execute(LoginDTO loginDTO);
}
