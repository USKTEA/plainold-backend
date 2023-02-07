package com.usktea.plainold.applications;

import com.usktea.plainold.applications.cancelRequest.CreateCancelRequestService;
import com.usktea.plainold.applications.order.GetOrderService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.dtos.CreateCancelRequestInput;
import com.usktea.plainold.exceptions.OrderNotBelongToUser;
import com.usktea.plainold.exceptions.OrderNotFound;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.cancelRequest.CancelRequest;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.CancelRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class CreateCancelRequestServiceTest {
    private GetUserService getUserService;
    private GetOrderService getOrderService;
    private CancelRequestRepository cancelRequestRepository;
    private CreateCancelRequestService createCancelRequestService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        getOrderService = mock(GetOrderService.class);
        cancelRequestRepository = mock(CancelRequestRepository.class);
        createCancelRequestService = new CreateCancelRequestService(getUserService, getOrderService, cancelRequestRepository);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");
        CreateCancelRequestInput createCancelRequestInput = CreateCancelRequestInput.fake(orderNumber);

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class,
                () -> createCancelRequestService.create(username, createCancelRequestInput));
    }

    @Test
    void whenOrderNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");
        CreateCancelRequestInput createCancelRequestInput = CreateCancelRequestInput.fake(orderNumber);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getOrderService.find(orderNumber)).willThrow(OrderNotFound.class);

        assertThrows(OrderNotFound.class,
                () -> createCancelRequestService.create(username, createCancelRequestInput));
    }

    @Test
    void whenOrdererNotMatch() {
        Username username = new Username("notTjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");
        CreateCancelRequestInput createCancelRequestInput = CreateCancelRequestInput.fake(orderNumber);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getOrderService.find(orderNumber)).willReturn(Order.fake(orderNumber));

        assertThrows(OrderNotBelongToUser.class,
                () -> createCancelRequestService.create(username, createCancelRequestInput));
    }

    @Test
    void whenCreateCancelRequestSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");
        CreateCancelRequestInput createCancelRequestInput = CreateCancelRequestInput.fake(orderNumber);

        Long id = 1L;
        CancelRequest cancelRequest = CancelRequest.fake(id);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getOrderService.find(orderNumber)).willReturn(Order.fake(orderNumber));
        given(cancelRequestRepository.save(any(CancelRequest.class)))
                .willReturn(cancelRequest);
        Long created = createCancelRequestService.create(username, createCancelRequestInput);

        assertThat(created).isEqualTo(id);
    }
}
