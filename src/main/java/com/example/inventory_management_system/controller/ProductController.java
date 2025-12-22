package com.example.inventory_management_system.controller;

import com.example.inventory_management_system.entity.Product;
import com.example.inventory_management_system.service.ProductService;
import com.example.inventory_management_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @PostMapping("/{subCategoryId}")
    public void save(@RequestBody Product product, @PathVariable Long subCategoryId) {
        Long userId = userService.getLoggedUserId();
        productService.saveProduct(userId, product, subCategoryId);
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        Long userId = userService.getLoggedUserId();
        return productService.getAllProducts(userId);
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        Long userId = userService.getLoggedUserId();
        return productService.findById(userId, productId);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword) {
        Long userId = userService.getLoggedUserId();
        return productService.searchProducts(keyword, userId);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard() {
        Long userId = userService.getLoggedUserId();
        return ResponseEntity.ok(productService.getDashboardData(userId));
    }

    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        Long userId = userService.getLoggedUserId();
        return productService.updateProduct(userId, productId, product);
    }

    @DeleteMapping("/{productId}")
    public String deleteById(@PathVariable Long productId) {
        Long userId = userService.getLoggedUserId();
        return productService.deleteProduct(userId, productId);
    }
}