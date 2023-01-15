package com.usktea.plainold.repositories;


import com.usktea.plainold.models.cart.Cart;
import com.usktea.plainold.models.user.Username;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Username> {
    Optional<Cart> findByUsername(Username username);
}
