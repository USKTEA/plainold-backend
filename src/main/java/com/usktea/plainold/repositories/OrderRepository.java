package com.usktea.plainold.repositories;

import com.usktea.plainold.models.Order;
import com.usktea.plainold.models.OrderNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, OrderNumber> {
}