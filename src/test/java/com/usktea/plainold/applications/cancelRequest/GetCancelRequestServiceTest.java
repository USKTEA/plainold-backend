package com.usktea.plainold.applications.cancelRequest;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.CancelRequestNotBelongToUser;
import com.usktea.plainold.exceptions.CancelRequestNotFound;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.cancelRequest.CancelRequest;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.CancelRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class GetCancelRequestServiceTest {
    private GetUserService getUserService;
    private CancelRequestRepository cancelRequestRepository;
    private GetCancelRequestService getCancelRequestService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        cancelRequestRepository = mock(CancelRequestRepository.class);
        getCancelRequestService = new GetCancelRequestService(getUserService, cancelRequestRepository);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class,
                () -> getCancelRequestService.getCancelRequest(username, orderNumber));
    }

    @Test
    void whenCancelRequestNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("notExists");

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(cancelRequestRepository.findByOrderNumber(orderNumber))
                .willReturn(Optional.empty());

        assertThrows(CancelRequestNotFound.class,
                () -> getCancelRequestService.getCancelRequest(username, orderNumber));
    }

    @Test
    void whenUserTryToGetNotHisOwnCancelRequest() {
        Username otherUser = new Username("notTjrxo1234@gmail.com");
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        CancelRequest cancelRequest = CancelRequest.fake(username);

        given(getUserService.find(otherUser)).willReturn(Users.fake(otherUser));
        given(cancelRequestRepository.findByOrderNumber(orderNumber))
                .willReturn(Optional.of(cancelRequest));

        assertThrows(CancelRequestNotBelongToUser.class,
                () -> getCancelRequestService.getCancelRequest(otherUser, orderNumber));
    }

    @Test
    void whenGetCancelRequestSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        CancelRequest cancelRequest = CancelRequest.fake(username);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(cancelRequestRepository.findByOrderNumber(orderNumber))
                .willReturn(Optional.of(cancelRequest));

        CancelRequest found = getCancelRequestService.getCancelRequest(username, orderNumber);

        assertThat(found.orderNumber()).isEqualTo(orderNumber);
    }
}
