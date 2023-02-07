package com.usktea.plainold.applications;

import com.usktea.plainold.applications.order.GetOrderDetailService;
import com.usktea.plainold.applications.order.GetOrderService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.dtos.OrderDetailDto;
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
class GetOrderDetailServiceTest {
    private GetUserService getUserService;
    private GetOrderService getOrderService;
    private GetOrderDetailService getOrderDetailService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        getOrderService = mock(GetOrderService.class);
        getOrderDetailService = new GetOrderDetailService(getUserService, getOrderService);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class,
                () -> getOrderDetailService.getOrder(username, orderNumber));
    }

    @Test
    void whenUserAndOrdererNotMatch() {
        Username username = new Username("notTjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getOrderService.find(orderNumber)).willReturn(Order.fake(orderNumber));

        assertThrows(OrderNotBelongToUser.class,
                () -> getOrderDetailService.getOrder(username, orderNumber));
    }

    @Test
    void whenOrderNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getOrderService.find(orderNumber)).willThrow(OrderNotFound.class);

        assertThrows(OrderNotFound.class,
                () -> getOrderDetailService.getOrder(username, orderNumber));
    }

    @Test
    void whenGetOrderSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getOrderService.find(orderNumber)).willReturn(Order.fake(orderNumber));

        OrderDetailDto orderDetail = getOrderDetailService.getOrder(username, orderNumber);

        assertThat(orderDetail.orderNumber()).isEqualTo(orderDetail.orderNumber());
    }
}
