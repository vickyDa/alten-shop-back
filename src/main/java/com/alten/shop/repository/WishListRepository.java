package com.alten.shop.repository;



import com.alten.shop.entity.User;
import com.alten.shop.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findByUser(User user);
}
