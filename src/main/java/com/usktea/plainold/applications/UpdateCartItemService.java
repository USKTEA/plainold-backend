package com.usktea.plainold.applications;

import com.usktea.plainold.exceptions.CartItemNotExists;
import com.usktea.plainold.models.cart.Cart;
import com.usktea.plainold.models.cart.Item;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.CartRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UpdateCartItemService {
    private final FindUserService findUserService;
    private final CartRepository cartRepository;

    public UpdateCartItemService(FindUserService findUserService, CartRepository cartRepository) {
        this.findUserService = findUserService;
        this.cartRepository = cartRepository;
    }

    public List<ProductId> updateItem(Username username, List<Item> items) {
        Users user = findUserService.find(username);

        Cart cart = cartRepository.findByUsername(user.username())
                .orElseThrow(CartItemNotExists::new);

        List<ProductId> updatedIds = cart.updateItem(items);

        return updatedIds;
    }
}