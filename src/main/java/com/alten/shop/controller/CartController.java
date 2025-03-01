package com.alten.shop.controller;


import com.alten.shop.entity.User;
import com.alten.shop.service.CartService;
import com.alten.shop.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private CartService cartService;
    private UserService userService;

    @PostMapping("/add")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> addProductToCart(@RequestParam Long productId, @RequestParam int quantity,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> user = userService.findByEmail(userDetails.getUsername());
        if(user.isPresent()){
            cartService.addProductToCart(user.get(), productId, quantity);
            return ResponseEntity.ok("Produit ajouté au panier");
        }
        return ResponseEntity.badRequest().body("Impossible d'ajouter au panier");
    }

}
