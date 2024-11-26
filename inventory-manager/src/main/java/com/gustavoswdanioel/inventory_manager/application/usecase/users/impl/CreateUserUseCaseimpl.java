package com.gustavoswdanioel.inventory_manager.application.usecase.users.impl;

import com.gustavoswdanioel.inventory_manager.application.dto.UserDTO;
import com.gustavoswdanioel.inventory_manager.application.mapper.UserMapper;
import com.gustavoswdanioel.inventory_manager.application.usecase.users.CreateUserUseCase;
import com.gustavoswdanioel.inventory_manager.domain.entity.Users;
import com.gustavoswdanioel.inventory_manager.domain.repository.UsersRepository;
import com.gustavoswdanioel.inventory_manager.domain.entity.AuditLogs;
import com.gustavoswdanioel.inventory_manager.domain.repository.AuditLogsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCaseimpl implements CreateUserUseCase {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuditLogsRepository auditLogsRepository;
    private static final Logger logger = LoggerFactory.getLogger(CreateUserUseCaseimpl.class);

    public CreateUserUseCaseimpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder,
                                 UserMapper userMapper, AuditLogsRepository auditLogsRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.auditLogsRepository = auditLogsRepository;
    }

    @Override
    public UserDTO execute(UserDTO userDTO) {
        logger.info("Register user {}", userDTO);

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);
        Users user = userMapper.toEntity(userDTO);
        Users savedUser = usersRepository.save(user);
        saveAuditLog(savedUser, "User created: " + userDTO.getEmail());

        logger.info("Registered user {}", userDTO);
        return userMapper.userDTO(savedUser);
    }

    private void saveAuditLog(Users user, String message) {
        AuditLogs auditLog = new AuditLogs();
        auditLog.setAction("CREATE_USER");
        auditLog.setData(message);

        auditLog.setUserId(null);
        auditLogsRepository.save(auditLog);
        logger.info("Audit log saved for action: {}, User: {}", "CREATE_USER", user.getEmail());
    }
}
