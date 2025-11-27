package com.example.inventory_management_system.service;

import com.example.inventory_management_system.entity.Product;
import com.example.inventory_management_system.entity.Users;
import com.example.inventory_management_system.repository.ProductRepository;
import com.example.inventory_management_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Product saveProduct(Long id, Product product) {
        Users user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));
        product.setUser(user);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(Long id) {
        return productRepository.findByUserId(id);
    }

    public Product updateProduct(Long userId, Long productId, Product product) {
        return productRepository.findByUserIdAndId(userId, productId)
                .map(existing -> {
                    if (product.getName() != null) {
                        existing.setName(product.getName());
                    }
                    if (product.getQuantity() != 0) {
                        existing.setQuantity(product.getQuantity());
                    }
                    if (product.getPrice() != 0) {
                        existing.setPrice(product.getPrice());
                    }
                    return productRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with id:" + userId));
    }

    public String deleteProduct(Long userid, Long productId) {
        Optional<Product> product = productRepository.findByUserIdAndId(userid, productId);
        if (!product.isEmpty()) {
            productRepository.deleteById(productId);
            return "Product deleted";
        }
        else return "Product not found";
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
}
