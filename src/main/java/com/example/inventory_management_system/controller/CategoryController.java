package com.example.inventory_management_system.controller;

import com.example.inventory_management_system.entity.Category;
import com.example.inventory_management_system.entity.SubCategory;
import com.example.inventory_management_system.service.CategoryService;
import com.example.inventory_management_system.service.SubCategoryService;
import com.example.inventory_management_system.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;

    public CategoryController(UserService userService, CategoryService categoryService, SubCategoryService subCategoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
    }

    @PostMapping()
    public void saveCategory(@RequestBody Category category) {
        Long userId = userService.getLoggedUserId();
        categoryService.saveCategory(userId, category);
    }

    @GetMapping()
    public List<Category> getAllGlobalCategories() {
        return categoryService.getAllGlobalCategories();
    }

    @GetMapping("/{categoryId}")
    public Optional<Category> getCategoryById(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @GetMapping("/{categoryId}/sub")
    public List<SubCategory> getSubCategories(@PathVariable Long categoryId) {
        return  subCategoryService.getAllSubCategories(categoryId);
    }


    @PutMapping("/{categoryId}")
    public Category updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        Long userId = userService.getLoggedUserId();
        return categoryService.updateCategory(userId, categoryId, category);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        Long userId = userService.getLoggedUserId();
        categoryService.deleteCategory(userId, categoryId);
    }

    @GetMapping("/user")
    public List<Category> getAllCategoriesWithUserCreated() {
        Long userId = userService.getLoggedUserId();
        return categoryService.getAllWithUser(userId);
    }
}
