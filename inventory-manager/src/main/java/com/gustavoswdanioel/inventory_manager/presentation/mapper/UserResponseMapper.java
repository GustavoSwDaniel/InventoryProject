package com.gustavoswdanioel.inventory_manager.presentation.mapper;

import com.gustavoswdanioel.inventory_manager.application.dto.UserDTO;
import com.gustavoswdanioel.inventory_manager.presentation.model.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {
    public UserResponse toResponse(UserDTO userDTO){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userDTO.getId());
        userResponse.setName(userDTO.getName());
        userResponse.setEmail(userDTO.getEmail());
        return userResponse;
    }
}
