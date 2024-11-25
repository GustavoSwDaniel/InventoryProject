package com.gustavoswdanioel.inventory_manager.domain.repository;

import com.gustavoswdanioel.inventory_manager.domain.entity.Products;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Products, Long>{

    @Modifying
    @Transactional
    @Query("UPDATE Products p SET p.active = false WHERE p.id = :id")
    void softDelete(@Param("id") Long id);

    Page<Products> findByActiveTrue(Pageable pageable);

}
