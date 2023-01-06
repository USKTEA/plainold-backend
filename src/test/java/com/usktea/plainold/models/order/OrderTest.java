package com.usktea.plainold.models.order;

import com.usktea.plainold.dtos.OrderItemDto;
import com.usktea.plainold.dtos.OrderRequest;
import com.usktea.plainold.dtos.OrderRequestDto;
import com.usktea.plainold.exceptions.EmptyOrderLines;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.user.UserName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
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

    @Test
    void whenOrderLinesIsEmpty() {
        OrderNumber orderNumber = new OrderNumber("tjrxo-202212312331");
        UserName userName = new UserName("tjrxo1234@gmail.com");
        List<OrderItemDto> orderItems = List.of();

        assertThrows(EmptyOrderLines.class, () ->
                new Order(orderNumber, OrderRequest.of(userName, OrderRequestDto.fake(orderItems)))
        );
    }
}
