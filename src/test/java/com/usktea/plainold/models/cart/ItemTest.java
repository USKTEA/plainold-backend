package com.usktea.plainold.models.cart;

import com.usktea.plainold.models.common.Quantity;
import com.usktea.plainold.models.product.ProductId;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class ItemTest {
    @Test
    void increaseQuantity() {
        ProductId productId = new ProductId(1L);

        Item item = Item.fake(productId);

        assertThat(item.getQuantity()).isEqualTo(new Quantity(1L));

        Item increased = item.increaseQuantity(new Quantity(2L));

        assertThat(increased.getQuantity()).isEqualTo(new Quantity(3L));
    }

    @Test
    void updateQuantity() {
        ProductId productId = new ProductId(1L);

        Item item = Item.fake(productId);

        assertThat(item.getQuantity()).isEqualTo(new Quantity(1L));

        Item increased = item.updateQuantity(new Quantity(5L));

        assertThat(increased.getQuantity()).isEqualTo(new Quantity(5L));
    }
}
