package com.usktea.plainold.applications.cart;

import com.usktea.plainold.applications.user.GetUserService;
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
public class DeleteCartItemService {
    private GetUserService getUserService;
    private CartRepository cartRepository;

    public DeleteCartItemService(GetUserService getUserService, CartRepository cartRepository) {
        this.getUserService = getUserService;
        this.cartRepository = cartRepository;
    }

    public List<ProductId> delete(Username username, List<Item> items) {
        Users user = getUserService.find(username);

        Cart cart = cartRepository.findByUsername(user.username())
                .orElseThrow(CartItemNotExists::new);

        List<ProductId> deletedIds = cart.deleteItem(items);

        return deletedIds;
    }
}
