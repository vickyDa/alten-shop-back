package com.alten.shop.service;

import com.alten.shop.entity.Cart;
import com.alten.shop.entity.CartItem;
import com.alten.shop.entity.Product;
import com.alten.shop.entity.User;
import com.alten.shop.repository.CartRepository;
import com.alten.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public void addProductToCart(User user, Long productId, int quantity) {
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> createCartForUser(user));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cart.getItems().add(cartItem);
        }
        cartRepository.save(cart);
    }

    @Transactional
    public Cart createCartForUser(User user) {
        if (cartRepository.existsByUser(user)) {
            throw new IllegalStateException("L'utilisateur possède déjà un panier.");
        }
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé pour l'utilisateur"));
    }

    public void updateProductQuantity(User user, Long productId, int quantity) {
        Cart cart = getCartByUser(user);
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produit non présent dans le panier"));
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
    }

    public void removeProductFromCart(User user, Long productId) {
        Cart cart = getCartByUser(user);
        boolean removed = cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        if (!removed) {
            throw new RuntimeException("Produit non présent dans le panier");
        }
        cartRepository.save(cart);
    }

    public void clearCart(User user) {
        Cart cart = getCartByUser(user);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
