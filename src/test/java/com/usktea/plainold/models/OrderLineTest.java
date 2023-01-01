package com.usktea.plainold.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderLineTest {

    @Test
    void equality() {
        ProductId productId = new ProductId(1L);
        Money price = new Money(1L);
        ProductName productName = new ProductName("T-Shirt");
        ThumbnailUrl thumbnailUrl = new ThumbnailUrl("1");
        Quantity quantity = new Quantity(2L);
        Money totalPrice = new Money(2L);

        OrderLine orderLine1 = new OrderLine(productId, price, productName, thumbnailUrl, quantity, totalPrice);
        OrderLine orderLine2 = new OrderLine(productId, price, productName, thumbnailUrl, quantity, totalPrice);

        assertThat(orderLine1).isEqualTo(orderLine2);
    }
}
