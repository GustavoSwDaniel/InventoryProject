package com.gustavoswdanioel.inventory_manager.application.usecase.users;

import com.gustavoswdanioel.inventory_manager.application.dto.UserDTO;

public interface CreateUserUseCase {
    UserDTO execute(UserDTO user);

}
