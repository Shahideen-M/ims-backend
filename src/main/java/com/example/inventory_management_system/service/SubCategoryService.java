package com.example.inventory_management_system.service;

import com.example.inventory_management_system.entity.SubCategory;
import com.example.inventory_management_system.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryService(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    public List<SubCategory> getAllSubCategories(Long categoryId) {
        return subCategoryRepository.findAllByCategoryId(categoryId);
    }
}
