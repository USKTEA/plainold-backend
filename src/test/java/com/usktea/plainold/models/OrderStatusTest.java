package com.usktea.plainold.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    void equality() {
        OrderStatus preparing1 = OrderStatus.PREPARING;
        OrderStatus preparing2 = OrderStatus.PREPARING;
        OrderStatus waiting = OrderStatus.PAYMENT_WAITING;

        assertThat(preparing1).isEqualTo(preparing2);
        assertThat(preparing1).isNotEqualTo(waiting);
    }
}
