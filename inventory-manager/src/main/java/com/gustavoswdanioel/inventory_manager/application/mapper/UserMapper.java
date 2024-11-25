package com.gustavoswdanioel.inventory_manager.application.mapper;

import com.gustavoswdanioel.inventory_manager.application.dto.UserDTO;
import com.gustavoswdanioel.inventory_manager.domain.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public Users toEntity(UserDTO userDTO){
        Users user = new Users();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return  user;
    }

    public UserDTO userDTO(Users user){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
