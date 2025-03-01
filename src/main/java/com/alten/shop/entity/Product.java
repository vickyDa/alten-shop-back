package com.alten.shop.entity;

import com.alten.shop.enumeration.InventoryStatus;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


import java.time.LocalDateTime;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int quantity;
    private String internalReference;
    private int shellId;
    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;
    private double rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // Getters et setters
}
