package com.gustavoswdanioel.inventory_manager.domain.repository;

import com.gustavoswdanioel.inventory_manager.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}
