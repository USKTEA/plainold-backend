package com.usktea.plainold.applications;

import com.usktea.plainold.applications.cart.AddCartItemService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.CartNotExists;
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
class AddCartItemServiceTest {
    private GetUserService getUserService;
    private CartRepository cartRepository;
    private AddCartItemService addCartItemService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        cartRepository = mock(CartRepository.class);
        addCartItemService = new AddCartItemService(getUserService, cartRepository);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gmail.com");

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        ProductId productId = new ProductId(1L);

        assertThrows(UserNotExists.class,
                () -> addCartItemService.addItem(username, List.of(Item.fake(productId))));
    }

    @Test
    void whenCartNotExists() {
        Username username = new Username("notExists@gmail.com");

        given(getUserService.find(username))
                .willReturn(Users.fake(username));

        given(cartRepository.findByUsername(username))
                .willThrow(CartNotExists.class);

        ProductId productId = new ProductId(1L);

        assertThrows(CartNotExists.class,
                () -> addCartItemService.addItem(username, List.of(Item.fake(productId))));
    }

    @Test
    void whenAddItemSuccess() {
        Username username = new Username("notExists@gmail.com");

        given(getUserService.find(username)).willReturn(Users.fake(username));

        given(cartRepository.findByUsername(username))
                .willReturn(Optional.of(new Cart(username)));

        ProductId productId = new ProductId(1L);

        int counts = addCartItemService.addItem(username, List.of(Item.fake(productId)));

        assertThat(counts).isEqualTo(1);
    }
}
