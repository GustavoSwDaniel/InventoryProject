package com.gustavoswdanioel.inventory_manager.application.usecase.users.impl;

import com.gustavoswdanioel.inventory_manager.application.dto.UserDTO;
import com.gustavoswdanioel.inventory_manager.application.mapper.UserMapper;
import com.gustavoswdanioel.inventory_manager.application.usecase.users.CreateUserUseCase;
import com.gustavoswdanioel.inventory_manager.domain.entity.Users;
import com.gustavoswdanioel.inventory_manager.domain.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCaseimpl implements CreateUserUseCase {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    public CreateUserUseCaseimpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder,
                                 UserMapper userMapper){
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO execute(UserDTO userDTO) {
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);
        Users user = userMapper.toEntity(userDTO);
        Users savedUser = usersRepository.save(user);
        return userMapper.userDTO(savedUser);
    }
}
