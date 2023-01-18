package com.usktea.plainold.applications;

import com.usktea.plainold.exceptions.OrderNotFound;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class GetOrderServiceTest {
    private OrderRepository orderRepository;
    private GetOrderService getOrderService;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        getOrderService = new GetOrderService(orderRepository);
    }

    @Test
    void findOrderFail() {
        OrderNumber orderNumber = new OrderNumber("notExists");

        given(orderRepository.findByOrderNumber(orderNumber))
                .willReturn(Optional.empty());

        assertThrows(OrderNotFound.class, () -> getOrderService.find(orderNumber));
    }

    @Test
    void findOrderSuccess() {
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-202301061131");

        Order order = Order.fake(orderNumber);

        given(orderRepository.findByOrderNumber(orderNumber))
                .willReturn(Optional.of(order));

        Order found = getOrderService.find(orderNumber);

        assertThat(order).isEqualTo(found);
    }
}
