package com.usktea.plainold.applications;

import com.usktea.plainold.applications.cart.GetCartService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.CartNotExists;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.cart.Cart;
import com.usktea.plainold.models.cart.Item;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class GetCartServiceTest {
    private CartRepository cartRepository;
    private GetUserService getUserService;
    private GetCartService getCartService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        cartRepository = mock(CartRepository.class);
        getCartService = new GetCartService(getUserService, cartRepository);
    }

    @Test
    void whenUserIsNotExists() {
        Username username = new Username("notExists@gmail.com");

        given(getUserService.find(username))
                .willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class, () -> getCartService.cart(username));
    }

    @Test
    void whenCartNotExists() {
        Username username = new Username("notExists@gmail.com");

        given(getUserService.find(username))
                .willReturn(Users.fake(username));

        given(cartRepository.findByUsername(username))
                .willThrow(CartNotExists.class);

    }

    @Test
    void whenCartExists() {
        Username username = new Username("notExists@gmail.com");

        given(getUserService.find(username))
                .willReturn(Users.fake(username));

        given(cartRepository.findByUsername(username))
                .willReturn(Optional.of(Cart.fake(username)));

        List<Item> items = getCartService.cart(username);

        assertThat(items).isNotNull();
    }
}
