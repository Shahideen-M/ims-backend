package com.example.inventory_management_system.service;

import com.example.inventory_management_system.entity.Product;
import com.example.inventory_management_system.entity.SubCategory;
import com.example.inventory_management_system.entity.Users;
import com.example.inventory_management_system.repository.CategoryRepository;
import com.example.inventory_management_system.repository.ProductRepository;
import com.example.inventory_management_system.repository.SubCategoryRepository;
import com.example.inventory_management_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    public void saveProduct(Long id, Product product, Long subCategoryId) {
        Users user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                        .orElseThrow(() -> new RuntimeException("Sub category not found"));
        product.setUser(user);
        product.setSubCategory(subCategory);
        productRepository.save(product);
    }

    public List<Product> getAllProducts(Long id) {
        return productRepository.findByUserId(id);
    }

    public Product updateProduct(Long userId, Long productId, Product product) {
        return productRepository.findByUserIdAndId(userId, productId)
                .map(existing -> {
                    if (product.getName() != null) existing.setName(product.getName());
                    if (product.getQuantity() != 0) existing.setQuantity(product.getQuantity());
                    if (product.getPrice() != 0) existing.setPrice(product.getPrice());
                    if (product.getSubCategory() != null) {
                        SubCategory sub =  subCategoryRepository.findById(product.getSubCategory().getId())
                                .orElseThrow(() -> new RuntimeException("Sub category not found"));
                        existing.setSubCategory(sub);
                    }
                    if (product.getDescription() != null) existing.setDescription(product.getDescription());
                    if (product.getShopAddress() != null) existing.setShopAddress(product.getShopAddress());
                    return productRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public String deleteProduct(Long userId, Long productId) {
        Optional<Product> product = productRepository.findByUserIdAndId(userId, productId);
        if (!product.isEmpty()) {
            productRepository.deleteById(productId);
            return "Product deleted";
        }
        else return "Product not found";
    }

    public Product findById(Long userId, Long id) {
        return productRepository.findById(id)
                .filter(product -> product.getUser().getId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Product> searchProducts(String keyword, Long userId) {
        return productRepository.findByNameContainingIgnoreCaseAndUserId(keyword, userId);
    }

    public Map<String, Object> getDashboardData(Long userId) {
        Map<String, Object> data = new HashMap<>();
        data.put("totalProducts", productRepository.countByUserId(userId));
        data.put("lowStock", productRepository.countByUserIdAndQuantityLessThan(userId, 5));
        data.put("outOfStock", productRepository.countByUserIdAndQuantity(userId, 0));
        data.put("totalCategories", categoryRepository.countByUserId(userId));
        return data;
    }

}
