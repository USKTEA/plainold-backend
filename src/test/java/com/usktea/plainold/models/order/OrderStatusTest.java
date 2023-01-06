package com.usktea.plainold.models.order;

import com.usktea.plainold.models.order.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
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
