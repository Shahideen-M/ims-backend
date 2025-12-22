package com.example.inventory_management_system.service;

import com.example.inventory_management_system.entity.Category;
import com.example.inventory_management_system.entity.Product;
import com.example.inventory_management_system.entity.Users;
import com.example.inventory_management_system.repository.CategoryRepository;
import com.example.inventory_management_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Category saveCategory(Long userId, Category category) {
        Users user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        category.setUser(user);
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long userId, Long categoryId, Category category) {
        return categoryRepository.findByUserIdAndId(userId, categoryId)
                .map(existing -> {
                    if (category.getName() != null) existing.setName(category.getName());
                    return categoryRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public String deleteCategory(Long userid, Long categoryId) {
        Optional<Category> category = categoryRepository.findByUserIdAndId(userid, categoryId);
        if (!category.isEmpty()) {
            categoryRepository.deleteById(categoryId);
            return "Category deleted";
        }
        else return "Category not found";
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Category> getAllGlobalCategories() {
        return categoryRepository.findAllWithoutUserId();
    }

    public List<Category> getAllWithUser(Long userId) {
        return categoryRepository.findByUserId(userId);
    }
}
