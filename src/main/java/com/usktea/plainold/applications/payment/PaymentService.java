package com.usktea.plainold.applications.payment;

import com.usktea.plainold.dtos.OrderItemDto;
import com.usktea.plainold.dtos.PaymentApproveRequest;
import com.usktea.plainold.dtos.PaymentReadyResponse;
import com.usktea.plainold.models.user.Username;

import java.util.List;

public interface PaymentService {
    PaymentReadyResponse ready(Username username, List<OrderItemDto> orderItems);

    String approve(Username username, PaymentApproveRequest paymentApproveRequest);
}
