package com.usktea.plainold.applications.cancelRequest;

import com.usktea.plainold.applications.order.GetOrderService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.dtos.CreateCancelRequestInput;
import com.usktea.plainold.models.cancelRequest.CancelRequest;
import com.usktea.plainold.models.cancelRequest.Content;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.CancelRequestRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CreateCancelRequestService {
    private final GetUserService getUserService;
    private final GetOrderService getOrderService;
    private final CancelRequestRepository cancelRequestRepository;

    public CreateCancelRequestService(GetUserService getUserService,
                                      GetOrderService getOrderService,
                                      CancelRequestRepository cancelRequestRepository) {
        this.getUserService = getUserService;
        this.getOrderService = getOrderService;
        this.cancelRequestRepository = cancelRequestRepository;
    }

    public Long create(Username username, CreateCancelRequestInput createCancelRequestInput) {
        OrderNumber orderNumber = createCancelRequestInput.orderNumber();
        Content content = createCancelRequestInput.content();

        Users user = getUserService.find(username);
        Order order = getOrderService.find(orderNumber);

        order.authenticate(username);

        CancelRequest cancelRequest = new CancelRequest(
                user.username(),
                order.orderNumber(),
                content
        );

        CancelRequest saved = cancelRequestRepository.save(cancelRequest);

        return saved.id();
    }
}
