package com.example.inventory_management_system.controller;

import com.example.inventory_management_system.entity.Product;
import com.example.inventory_management_system.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add/{id}")
    public void save(@PathVariable Long id, @RequestBody Product product) {
        productService.saveProduct(id, product);
    }

    @GetMapping("/all/{id}")
    public List<Product> getAllProducts(@PathVariable Long id) {
        return productService.getAllProducts(id);
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PutMapping("/update/{userId}/{productId}")
    public Product updateProduct(@PathVariable Long userId, @PathVariable Long productId, @RequestBody Product product) {
        return productService.updateProduct(userId, productId, product);
    }

    @DeleteMapping("/delete/{userId}/{productId}")
    public String deleteById(@PathVariable Long userId, @PathVariable Long productId) {
        return productService.deleteProduct(userId, productId);
    }
    @GetMapping("/ping")
    public String ping() {
        return "Server is alive!";
    }
}