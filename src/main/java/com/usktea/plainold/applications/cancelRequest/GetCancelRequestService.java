package com.usktea.plainold.applications.cancelRequest;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.CancelRequestNotFound;
import com.usktea.plainold.models.cancelRequest.CancelRequest;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.CancelRequestRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class GetCancelRequestService {
    private final GetUserService getUserService;
    private final CancelRequestRepository cancelRequestRepository;

    public GetCancelRequestService(GetUserService getUserService, CancelRequestRepository cancelRequestRepository) {
        this.getUserService = getUserService;
        this.cancelRequestRepository = cancelRequestRepository;
    }

    public CancelRequest getCancelRequest(Username username, OrderNumber orderNumber) {
        Users user = getUserService.find(username);
        CancelRequest cancelRequest = cancelRequestRepository.findByOrderNumber(orderNumber)
                .orElseThrow(CancelRequestNotFound::new);

        cancelRequest.verifyUser(user.username());

        return cancelRequest;
    }
}
