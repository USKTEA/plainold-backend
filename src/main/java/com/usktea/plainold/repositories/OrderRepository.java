package com.usktea.plainold.repositories;

import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, OrderNumber>, JpaSpecificationExecutor {
    Optional<Order> findByOrderNumber(OrderNumber orderNumber);
}