package com.alten.shop.service;


import com.alten.shop.entity.Product;
import com.alten.shop.entity.User;
import com.alten.shop.entity.WishList;
import com.alten.shop.repository.UserRepository;
import com.alten.shop.repository.WishListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WishListService {

    private final WishListRepository wishlistRepository;
    private final UserRepository userRepository;

    public WishListService(WishListRepository wishlistRepository, UserRepository userRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
    }

    public WishList getOrCreateWishListForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return wishlistRepository.findByUser(user)
                .orElseGet(() -> {
                    WishList wishlist = new WishList();
                    wishlist.setUser(user);
                    return wishlistRepository.save(wishlist);
                });
    }

    public void addProductToWishlist(Long userId, Product product) {
        WishList wishlist = getOrCreateWishListForUser(userId);
        wishlist.addProduct(product);
        wishlistRepository.save(wishlist);
    }

    public void removeProductFromWishlist(Long userId, Product product) {
        WishList wishlist = getOrCreateWishListForUser(userId);
        wishlist.removeProduct(product);
        wishlistRepository.save(wishlist);
    }
}
