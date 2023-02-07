package com.usktea.plainold.repositories;

import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.user.Username;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, OrderNumber>, JpaSpecificationExecutor {
    Optional<Order> findByOrderNumber(OrderNumber orderNumber);

    List<Order> findAllByUsername(Username username, Sort sort);
}