package com.usktea.plainold.applications;

import com.usktea.plainold.applications.order.CancelOrderService;
import com.usktea.plainold.applications.order.GetOrderService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.OrderNotBelongToUser;
import com.usktea.plainold.exceptions.OrderNotFound;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class CancelOrderServiceTest {
    private GetUserService getUserService;
    private GetOrderService getOrderService;
    private CancelOrderService cancelOrderService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        getOrderService = mock(GetOrderService.class);
        cancelOrderService = new CancelOrderService(getUserService, getOrderService);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class,
                () -> cancelOrderService.cancel(username, orderNumber));
    }

    @Test
    void whenOrderNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getOrderService.find(orderNumber)).willThrow(OrderNotFound.class);

        assertThrows(OrderNotFound.class,
                () -> cancelOrderService.cancel(username, orderNumber));
    }

    @Test
    void whenUserTryToCancelNotHisOwnOrder() {
        Username username = new Username("notTjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getOrderService.find(orderNumber)).willReturn(Order.fake(orderNumber));

        assertThrows(OrderNotBelongToUser.class,
                () -> cancelOrderService.cancel(username, orderNumber));
    }

    @Test
    void whenCancelOrderSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getOrderService.find(orderNumber)).willReturn(Order.fake(orderNumber));

        OrderNumber canceled = cancelOrderService.cancel(username, orderNumber);

        assertThat(canceled).isEqualTo(orderNumber);
    }
}
