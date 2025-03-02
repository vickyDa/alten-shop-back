package com.alten.shop.controller;


import com.alten.shop.entity.Cart;
import com.alten.shop.entity.User;
import com.alten.shop.repository.UserRepository;
import com.alten.shop.service.CartService;
import com.alten.shop.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
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


//    @PostMapping("/add")
//    @SecurityRequirement(name = "bearerAuth")
//    public ResponseEntity<?> addProductToCart(@RequestParam Long productId, @RequestParam int quantity,
//                                              @AuthenticationPrincipal UserDetails userDetails) {
//        Optional<User> user = userService.findByEmail(userDetails.getUsername());
//        if(user.isPresent()){
//            cartService.addProductToCart(user.get(), productId, quantity);
//            return ResponseEntity.ok("Produit ajouté au panier");
//        }
//        return ResponseEntity.badRequest().body("Impossible d'ajouter au panier");
//    }


    // Endpoint pour ajouter un produit au panier
    // Exemple d'appel : POST /api/cart/1/add/100?quantity=2
    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<String> addProductToCart(@PathVariable Long userId,
                                                   @PathVariable Long productId,
                                                   @RequestParam(defaultValue = "1") int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        cartService.addProductToCart(user, productId, quantity);
        return ResponseEntity.ok("Produit ajouté au panier");
    }

    // Endpoint pour récupérer le panier d'un utilisateur
    // Exemple d'appel : GET /api/cart/1
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Cart cart = cartService.getCartByUser(user);
        return ResponseEntity.ok(cart);
    }

    // Endpoint pour mettre à jour la quantité d'un produit dans le panier
    // Exemple d'appel : PUT /api/cart/1/update/100?quantity=5
    @PutMapping("/{userId}/update/{productId}")
    public ResponseEntity<String> updateProductQuantity(@PathVariable Long userId,
                                                        @PathVariable Long productId,
                                                        @RequestParam int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        cartService.updateProductQuantity(user, productId, quantity);
        return ResponseEntity.ok("Quantité mise à jour");
    }

    // Endpoint pour retirer un produit du panier
    // Exemple d'appel : DELETE /api/cart/1/remove/100
    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable Long userId,
                                                        @PathVariable Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        cartService.removeProductFromCart(user, productId);
        return ResponseEntity.ok("Produit retiré du panier");
    }

    // Endpoint pour vider complètement le panier
    // Exemple d'appel : DELETE /api/cart/1/clear
    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        cartService.clearCart(user);
        return ResponseEntity.ok("Panier vidé");
    }
}
