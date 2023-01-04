package com.usktea.plainold.repositories;

import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, OrderNumber> {
}