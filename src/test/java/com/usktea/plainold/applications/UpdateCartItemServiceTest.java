package com.usktea.plainold.applications;

import com.usktea.plainold.exceptions.CartItemNotExists;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.cart.Cart;
import com.usktea.plainold.models.cart.Item;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class UpdateCartItemServiceTest {
    private GetUserService getUserService;
    private CartRepository cartRepository;
    private UpdateCartItemService updateCartItemService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        cartRepository = mock(CartRepository.class);
        updateCartItemService = new UpdateCartItemService(getUserService, cartRepository);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gmail.com");

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        ProductId productId = new ProductId(1L);

        assertThrows(UserNotExists.class,
                () -> updateCartItemService.updateItem(username, List.of(Item.fake(productId))));
    }

    @Test
    void whenUpdateItemSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(1L);

        given(getUserService.find(username)).willReturn(Users.fake(username));

        Cart cart = Cart.fake(username);

        given(cartRepository.findByUsername(username))
                .willReturn(Optional.of(cart));

        List<Item> items = List.of(Item.fake(productId));

        cart.addItem(items);

        List<ProductId> updatedIds = updateCartItemService.updateItem(username, items);

        assertThat(updatedIds).isEqualTo(List.of(productId));
    }

    @Test
    void whenItemNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(1L);

        given(getUserService.find(username)).willReturn(Users.fake(username));

        Cart cart = Cart.fake(username);

        assertThat(cart.countItems()).isEqualTo(0);

        given(cartRepository.findByUsername(username))
                .willReturn(Optional.of(Cart.fake(username)));

        assertThrows(CartItemNotExists.class,
                () -> updateCartItemService.updateItem(username, List.of(Item.fake(productId))));
    }
}
