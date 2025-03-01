package com.alten.shop.repository;


import com.alten.shop.entity.Cart;
import com.alten.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);

    boolean existsByUser(User user);
}