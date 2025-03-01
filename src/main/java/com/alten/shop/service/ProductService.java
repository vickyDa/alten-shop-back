package com.alten.shop.service;

import com.alten.shop.entity.Product;
import com.alten.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setCategory(productDetails.getCategory());
            product.setCode(productDetails.getCode());
            product.setImage(productDetails.getImage());
            product.setPrice(productDetails.getPrice());
            product.setInternalReference(productDetails.getInternalReference());
            product.setQuantity(productDetails.getQuantity());
            product.setInventoryStatus(productDetails.getInventoryStatus());
            product.setShellId(productDetails.getShellId());
            product.setRating(productDetails.getRating());

            product.setUpdatedAt(LocalDateTime.now());
            return productRepository.save(product);
        });
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id).map(product -> {
            productRepository.delete(product);
            return true;
        }).orElse(false);
    }
}
