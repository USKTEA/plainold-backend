package com.usktea.plainold.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @Test
    void creation() {
        Order order = Order.fake(new OrderNumber("tjrxo1234-202212312331"));

        assertThat(order).isNotNull();
    }

    @Test
    void equality() {
        Order order1 = Order.fake(new OrderNumber("tjrxo1234-202212312331"));
        Order order2 = Order.fake(new OrderNumber("tjrxo1234-202212312331"));
        Order order3 = Order.fake(new OrderNumber("tjrxo-202212312331"));

        assertThat(order1).isEqualTo(order2);
        assertThat(order1).isNotEqualTo(order3);
    }
}
