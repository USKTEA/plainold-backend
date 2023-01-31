package com.usktea.plainold.applications;

import com.usktea.plainold.models.cart.Cart;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.repositories.CartRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CreateCartService {
    private final CartRepository cartRepository;

    public CreateCartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void create(Username username) {
        cartRepository.save(new Cart(username));
    }
}
