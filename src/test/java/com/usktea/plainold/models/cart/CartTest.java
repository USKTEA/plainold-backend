package com.usktea.plainold.models.cart;

import com.usktea.plainold.exceptions.CartItemNotExists;
import com.usktea.plainold.models.common.Quantity;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
class CartTest {
    @Test
    void addItem() {
        Username username = new Username("tjrxo1234@gmail.com");
        Cart cart = new Cart(username);

        assertThat(cart.items()).hasSize(0);

        cart.addItem(List.of(Item.fake(new ProductId(1L))));

        assertThat(cart.items()).hasSize(1);
    }

    @Test
    void whenAddSameItem() {
        Username username = new Username("tjrxo1234@gmail.com");
        Cart cart = new Cart(username);

        assertThat(cart.items()).hasSize(0);

        cart.addItem(List.of(Item.fake(new ProductId(1L))));

        assertThat(cart.items()).hasSize(1);
        assertThat(cart.items().get(0).getQuantity()).isEqualTo(new Quantity(1L));

        cart.addItem(List.of(Item.fake(new ProductId(1L))));

        assertThat(cart.items()).hasSize(1);
        assertThat(cart.items().get(0).getQuantity()).isEqualTo(new Quantity(2L));
    }

    @Test
    void updateItem() {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(1L);
        List<Item> items = List.of(Item.fake(productId));

        Cart cart = new Cart(username);

        cart.addItem(items);

        List<ProductId> updatedIds = cart.updateItem(items);

        assertThat(updatedIds).isEqualTo(List.of(productId));
    }

    @Test
    void deleteItem() {
        Username username = new Username("tjrxo1234@gmaila.com");
        ProductId productId = new ProductId(1L);
        List<Item> items = List.of(Item.fake(productId));

        Cart cart = new Cart(username);

        cart.addItem(items);

        List<ProductId> productIds = cart.deleteItem(items);

        assertThat(productIds).isEqualTo(productIds);
    }

    @Test
    void whenItemNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(1L);
        List<Item> items = List.of(Item.fake(productId));

        Cart cart = new Cart(username);

        assertThat(cart.countItems()).isEqualTo(0);

        assertThrows(CartItemNotExists.class, () -> cart.updateItem(items));
    }

    @Test
    void equality() {
        Username username1 = new Username("tjrxo1234@gmail.com");
        Username username2 = new Username("notTjrxo1234@gmail.com");

        Cart cart1 = new Cart(username1);
        Cart cart2 = new Cart(username1);
        Cart cart3 = new Cart(username2);

        assertThat(cart1).isEqualTo(cart2);
        assertThat(cart1).isNotEqualTo(cart3);
    }
}
