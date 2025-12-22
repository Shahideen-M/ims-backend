package com.example.inventory_management_system.repository;

import com.example.inventory_management_system.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByUserId(Long userId);
    Optional<Product> findByUserIdAndId(Long userId, Long productId);
    List<Product> findByNameContainingIgnoreCaseAndUserId(String keyword,  Long userId);

    long countByUserId(Long userId);

    long countByUserIdAndQuantityLessThan(Long userId, int qty);

    long countByUserIdAndQuantity(Long userId, int qty);

}
