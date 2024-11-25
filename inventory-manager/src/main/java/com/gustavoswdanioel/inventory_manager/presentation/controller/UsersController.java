package com.gustavoswdanioel.inventory_manager.presentation.controller;

import com.gustavoswdanioel.inventory_manager.application.dto.LoginAuthenticatedDTO;
import com.gustavoswdanioel.inventory_manager.application.dto.LoginDTO;
import com.gustavoswdanioel.inventory_manager.application.dto.UserDTO;
import com.gustavoswdanioel.inventory_manager.application.mapper.UserMapper;
import com.gustavoswdanioel.inventory_manager.application.usecase.users.CreateUserUseCase;
import com.gustavoswdanioel.inventory_manager.application.usecase.users.LoginUserUseCase;
import com.gustavoswdanioel.inventory_manager.presentation.mapper.CreateUserRequest;
import com.gustavoswdanioel.inventory_manager.presentation.mapper.LoginResponseMapper;
import com.gustavoswdanioel.inventory_manager.presentation.mapper.LoginUserRequest;
import com.gustavoswdanioel.inventory_manager.presentation.mapper.UserResponseMapper;
import com.gustavoswdanioel.inventory_manager.presentation.model.LoginResponse;
import com.gustavoswdanioel.inventory_manager.presentation.model.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final CreateUserUseCase createUserUseCase;
    private final UserResponseMapper userResponseMapper;
    private final LoginUserUseCase loginUserUseCase;
    private final LoginResponseMapper loginResponseMapper;

    public UsersController(CreateUserUseCase createUserUseCase,
                           UserResponseMapper userResponseMapper,
                           LoginUserUseCase loginUserUseCase,
                           LoginResponseMapper loginResponseMapper){
        this.createUserUseCase = createUserUseCase;
        this.userResponseMapper = userResponseMapper;
        this.loginUserUseCase = loginUserUseCase;
        this.loginResponseMapper = loginResponseMapper;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest createUserRequest){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(createUserRequest.getName());
        userDTO.setEmail(createUserRequest.getEmail());
        userDTO.setPassword(createUserRequest.getPassword());
        UserDTO createdUserDTO = createUserUseCase.execute(userDTO);
        UserResponse userResponse = userResponseMapper.toResponse(createdUserDTO);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserRequest loginUserRequest){
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(loginUserRequest.getEmail());
        loginDTO.setPassword(loginUserRequest.getPassword());
        LoginAuthenticatedDTO loginAuthenticated = loginUserUseCase.execute(loginDTO);
        LoginResponse loginResponse = loginResponseMapper.toResponse(loginAuthenticated);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
}
