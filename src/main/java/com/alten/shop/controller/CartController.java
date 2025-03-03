package com.alten.shop.controller;


import com.alten.shop.entity.Cart;
import com.alten.shop.entity.User;
import com.alten.shop.repository.UserRepository;
import com.alten.shop.service.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    public CartController(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }


    @PostMapping("/{userId}/add/{productId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> addProductToCart(@PathVariable Long userId,
                                                   @PathVariable Long productId,
                                                   @RequestParam(defaultValue = "1") int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        cartService.addProductToCart(user, productId, quantity);
        return ResponseEntity.ok("Produit ajouté au panier");
    }


    @GetMapping("/{userId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Cart cart = cartService.getCartByUser(user);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/{userId}/update/{productId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> updateProductQuantity(@PathVariable Long userId,
                                                        @PathVariable Long productId,
                                                        @RequestParam int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        cartService.updateProductQuantity(user, productId, quantity);
        return ResponseEntity.ok("Quantité mise à jour");
    }


    @DeleteMapping("/{userId}/remove/{productId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> removeProductFromCart(@PathVariable Long userId,
                                                        @PathVariable Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        cartService.removeProductFromCart(user, productId);
        return ResponseEntity.ok("Produit retiré du panier");
    }


    @DeleteMapping("/{userId}/clear")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        cartService.clearCart(user);
        return ResponseEntity.ok("Panier vidé");
    }
}
