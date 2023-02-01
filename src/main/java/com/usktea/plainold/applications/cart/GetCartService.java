package com.usktea.plainold.applications.cart;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.CartNotExists;
import com.usktea.plainold.models.cart.Cart;
import com.usktea.plainold.models.cart.Item;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.CartRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GetCartService {
    private final GetUserService getUserService;
    private final CartRepository cartRepository;

    public GetCartService(GetUserService getUserService, CartRepository cartRepository) {
        this.getUserService = getUserService;
        this.cartRepository = cartRepository;
    }

    public List<Item> cart(Username username) {
        Users user = getUserService.find(username);

        Cart cart = cartRepository.findByUsername(user.username())
                .orElseThrow(CartNotExists::new);

        return cart.items();
    }
}
