package com.usktea.plainold.applications.order;

import com.usktea.plainold.exceptions.OrderNotFound;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class GetOrderService {
    private final OrderRepository orderRepository;

    public GetOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order find(OrderNumber orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(OrderNotFound::new);

        return order;
    }
}
