package com.usktea.plainold.applications.order;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.GuestIsNotAuthorized;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.order.OrderStatus;
import com.usktea.plainold.models.user.Role;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class GetUserOrderServiceTest {
    private GetUserService getUserService;
    private OrderRepository orderRepository;
    private GetUserOrderService getUserOrderService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        orderRepository = mock(OrderRepository.class);
        getUserOrderService = new GetUserOrderService(getUserService, orderRepository);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gmail.com");

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class, () -> getUserOrderService.orders(username));
    }

    @Test
    void whenGuestTryToGetOrders() {
        Username guestName = new Username("guest");
        Users guest = Users.fake(guestName, Role.GUEST);

        given(getUserService.find(guest.username())).willReturn(guest);

        assertThrows(GuestIsNotAuthorized.class, () -> getUserOrderService.orders(guestName));
    }

    @Test
    void whenGetOrdersSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(orderRepository.findAllByUsername(any(Username.class), any(Sort.class)))
                .willReturn(List.of(Order.fake(orderNumber)));

        List<Order> orders = getUserOrderService.orders(username);

        assertThat(orders).hasSize(1);
    }

    @Test
    void whenGetOrdersWithStatus() {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");
        OrderStatus orderStatus = OrderStatus.CANCELED;

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(orderRepository.findAll(any(Specification.class), any(Sort.class)))
                .willReturn(List.of(Order.fake(orderNumber, orderStatus)));

        List<Order> orders = getUserOrderService.orders(username, orderStatus);

        assertThat(orders.get(0).status()).isEqualTo(orderStatus);
    }
}
