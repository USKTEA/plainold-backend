package com.usktea.plainold.repositories;

import com.usktea.plainold.models.cancelRequest.CancelRequest;
import com.usktea.plainold.models.order.OrderNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CancelRequestRepository extends JpaRepository<CancelRequest, Long> {
    Optional<CancelRequest> findByOrderNumber(OrderNumber orderNumber);
}
