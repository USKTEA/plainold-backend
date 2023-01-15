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
class DeleteCartItemServiceTest {
    private FindUserService findUserService;
    private CartRepository cartRepository;
    private DeleteCartItemService deleteCartItemService;

    @BeforeEach
    void setup() {
        findUserService = mock(FindUserService.class);
        cartRepository = mock(CartRepository.class);
        deleteCartItemService = new DeleteCartItemService(findUserService, cartRepository);
    }

    @Test
    void whenDeleteItemSuccess() {
        Username username = new Username("notExists@gmail.com");
        ProductId productId = new ProductId(1L);
        List<Item> items = List.of(Item.fake(productId));

        Cart cart = Cart.fake(username);

        given(findUserService.find(username)).willReturn(Users.fake(username));

        given(cartRepository.findByUsername(username))
                .willReturn(Optional.of(cart));

        assertThat(cart.countItems()).isEqualTo(0);

        cart.addItem(items);

        List<ProductId> deletedIds = cart.deleteItem(items);

        assertThat(deletedIds).isEqualTo(List.of(productId));
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gmail.com");
        ProductId productId = new ProductId(1L);
        List<Item> items = List.of(Item.fake(productId));

        given(findUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class,
                () -> deleteCartItemService.delete(username, items));
    }

    @Test
    void whenCartItemNotExists() {
        Username username = new Username("notExists@gmail.com");
        ProductId productId = new ProductId(1L);
        List<Item> items = List.of(Item.fake(productId));

        Cart cart = Cart.fake(username);

        given(findUserService.find(username)).willReturn(Users.fake(username));

        given(cartRepository.findByUsername(username))
                .willReturn(Optional.of(cart));

        assertThat(cart.countItems()).isEqualTo(0);

        assertThrows(CartItemNotExists.class, () -> cart.deleteItem(items));
    }
}
