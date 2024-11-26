package com.gustavoswdanioel.inventory_manager.application.usecase.users.impl;

import com.gustavoswdanioel.inventory_manager.application.dto.LoginAuthenticatedDTO;
import com.gustavoswdanioel.inventory_manager.application.dto.LoginDTO;
import com.gustavoswdanioel.inventory_manager.application.exception.AuthenticatedErrorException;
import com.gustavoswdanioel.inventory_manager.application.exception.UserNotFoundException;
import com.gustavoswdanioel.inventory_manager.application.mapper.LoginAuthenticatedMapper;
import com.gustavoswdanioel.inventory_manager.application.usecase.users.LoginUserUseCase;
import com.gustavoswdanioel.inventory_manager.domain.entity.Users;
import com.gustavoswdanioel.inventory_manager.domain.repository.UsersRepository;
import com.gustavoswdanioel.inventory_manager.domain.entity.AuditLogs;
import com.gustavoswdanioel.inventory_manager.domain.repository.AuditLogsRepository;
import com.gustavoswdanioel.inventory_manager.infrastructure.adapter.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginUseUseCaseImpl implements LoginUserUseCase {
    private final UsersRepository usersRepository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final LoginAuthenticatedMapper loginAuthenticatedMapper;
    private final AuditLogsRepository auditLogsRepository;  // Repositório de logs
    private static final Logger logger = LoggerFactory.getLogger(LoginUseUseCaseImpl.class);

    public LoginUseUseCaseImpl(UsersRepository usersRepository, JwtTokenService jwtTokenService,
                               PasswordEncoder passwordEncoder,
                               LoginAuthenticatedMapper loginAuthenticatedMapper,
                               AuditLogsRepository auditLogsRepository) {
        this.usersRepository = usersRepository;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
        this.loginAuthenticatedMapper = loginAuthenticatedMapper;
        this.auditLogsRepository = auditLogsRepository;
    }

    @Override
    public LoginAuthenticatedDTO execute(LoginDTO loginDTO) {
        logger.info("Starting login for user: {}", loginDTO.getEmail());
        Users user = usersRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> {
                    saveAuditLog(loginDTO.getEmail(), "FAILED_LOGIN", "User not found", null);
                    return new UserNotFoundException("User not found");
                });

        if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            String token = jwtTokenService.generateToken(user.getEmail());
            logger.info("User authenticated: {}", user.getEmail());
            saveAuditLog(user.getEmail(), "SUCCESSFUL_LOGIN", "User authenticated", user);
            return loginAuthenticatedMapper.toDTO(token);
        }

        saveAuditLog(loginDTO.getEmail(), "FAILED_LOGIN", "Incorrect password", user);
        throw new AuthenticatedErrorException("Authentication error");
    }

    private void saveAuditLog(String email, String action, String message, Users user) {
        AuditLogs auditLog = new AuditLogs();
        auditLog.setAction(action);
        auditLog.setData(String.format("User: %s, Message: %s", email, message));

        if (user != null) {
            auditLog.setUserId(user.getId());
        } else {
            auditLog.setUserId(null);
        }

        auditLogsRepository.save(auditLog);
        logger.info("Audit log saved for action: {}, User: {}", action, email);
    }
}
