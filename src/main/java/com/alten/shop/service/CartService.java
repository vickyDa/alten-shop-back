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

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        cart.getItems().add(cartItem);
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
}
