package com.alten.shop.controller;

import com.alten.shop.entity.Product;
import com.alten.shop.entity.WishList;
import com.alten.shop.repository.ProductRepository;
import com.alten.shop.service.WishListService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.Optional;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    private final WishListService wishlistService;
    private final ProductRepository productRepository;

    public WishListController(WishListService wishlistService, ProductRepository productRepository) {
        this.wishlistService = wishlistService;
        this.productRepository = productRepository;
    }

    @GetMapping("/{userId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<WishList> getWishlist(@PathVariable Long userId) {
        WishList wishlist = wishlistService.getOrCreateWishListForUser(userId);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/{userId}/add")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> addProductToWishlist(@PathVariable Long userId, @RequestParam Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produit non trouvé");
        }
        wishlistService.addProductToWishlist(userId, optionalProduct.get());
        return ResponseEntity.ok("Produit ajouté à la wishlist");
    }

    @DeleteMapping("/{userId}/remove")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> removeProductFromWishlist(@PathVariable Long userId, @RequestParam Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produit non trouvé");
        }
        wishlistService.removeProductFromWishlist(userId, optionalProduct.get());
        return ResponseEntity.ok("Produit retiré de la wishlist");
    }
}
