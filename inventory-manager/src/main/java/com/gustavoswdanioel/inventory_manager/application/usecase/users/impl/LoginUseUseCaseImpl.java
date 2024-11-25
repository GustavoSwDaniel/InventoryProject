package com.gustavoswdanioel.inventory_manager.application.usecase.users.impl;

import com.gustavoswdanioel.inventory_manager.application.dto.LoginAuthenticatedDTO;
import com.gustavoswdanioel.inventory_manager.application.dto.LoginDTO;
import com.gustavoswdanioel.inventory_manager.application.exception.AuthenticatedErrorException;
import com.gustavoswdanioel.inventory_manager.application.exception.UserNotFoundException;
import com.gustavoswdanioel.inventory_manager.application.mapper.LoginAuthenticatedMapper;
import com.gustavoswdanioel.inventory_manager.application.usecase.users.LoginUserUseCase;
import com.gustavoswdanioel.inventory_manager.domain.entity.Users;
import com.gustavoswdanioel.inventory_manager.domain.repository.UsersRepository;
import com.gustavoswdanioel.inventory_manager.infrastructure.adapter.JwtTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginUseUseCaseImpl implements LoginUserUseCase {
    private final UsersRepository usersRepository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final LoginAuthenticatedMapper loginAuthenticatedMapper;

    public LoginUseUseCaseImpl(UsersRepository usersRepository, JwtTokenService jwtTokenService,
                               PasswordEncoder passwordEncoder,
                               LoginAuthenticatedMapper loginAuthenticatedMapper) {
        this.usersRepository = usersRepository;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
        this.loginAuthenticatedMapper = loginAuthenticatedMapper;
    }

    @Override
    public LoginAuthenticatedDTO execute(LoginDTO loginDTO) {
        Users user = usersRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            String token = jwtTokenService.generateToken(user.getEmail());
            return loginAuthenticatedMapper.toDTO(token);
        }

        throw new AuthenticatedErrorException("Authentication error");
    }
}
