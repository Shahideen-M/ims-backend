package com.example.inventory_management_system.repository;

import com.example.inventory_management_system.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserId(Long userId);
    Optional<Category> findByUserIdAndId(Long userId, Long categoryId);
    @Query("SELECT c FROM Category c WHERE c.user IS NULL")
    List<Category> findAllWithoutUserId();

    long countByUserId(Long userId);
}
