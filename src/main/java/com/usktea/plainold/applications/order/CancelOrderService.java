package com.usktea.plainold.applications.order;

import com.usktea.plainold.applications.order.GetOrderService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CancelOrderService {
    private final GetUserService getUserService;
    private final GetOrderService getOrderService;

    public CancelOrderService(GetUserService getUserService, GetOrderService getOrderService) {
        this.getUserService = getUserService;
        this.getOrderService = getOrderService;
    }

    public OrderNumber cancel(Username username, OrderNumber orderNumber) {
        Users user = getUserService.find(username);
        Order order = getOrderService.find(orderNumber);

        order.markCancel(user.username());

        return order.orderNumber();
    }
}
