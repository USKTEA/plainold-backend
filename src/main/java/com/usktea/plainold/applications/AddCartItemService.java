package com.usktea.plainold.applications;

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
public class AddCartItemService {
    private final FindUserService findUserService;
    private final CartRepository cartRepository;

    public AddCartItemService(FindUserService findUserService, CartRepository cartRepository) {
        this.findUserService = findUserService;
        this.cartRepository = cartRepository;
    }

    public int addItem(Username username, List<Item> items) {
        Users user = findUserService.find(username);

        Cart cart = cartRepository.findByUsername(user.username())
                .orElseThrow(CartNotExists::new);

        cart.addItem(items);

        return cart.countItems();
    }
}
