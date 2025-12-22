package com.example.inventory_management_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;
    private int quantity;
    private double price;
    private String description;
    private String shopAddress;

    public Product() {}

    public Product(String name, int quantity, double price, SubCategory subCategory, String description,  String shopAddress) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.subCategory = subCategory;
        this.description = description;
        this.shopAddress = shopAddress;
    }

    @ManyToOne
    @JoinColumn(name = "user_Id")
    @JsonIgnore
    private Users user;

    @ManyToOne
    @JoinColumn(name = "subcategory_Id")
    private SubCategory subCategory;
}
