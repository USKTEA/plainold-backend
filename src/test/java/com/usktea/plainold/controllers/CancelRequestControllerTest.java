package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.cancelRequest.CreateCancelRequestService;
import com.usktea.plainold.applications.cancelRequest.GetCancelRequestService;
import com.usktea.plainold.dtos.CreateCancelRequestInput;
import com.usktea.plainold.exceptions.CancelRequestNotBelongToUser;
import com.usktea.plainold.exceptions.CancelRequestNotFound;
import com.usktea.plainold.exceptions.OrderNotBelongToUser;
import com.usktea.plainold.exceptions.OrderNotFound;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.cancelRequest.CancelRequest;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(CancelRequestController.class)
class CancelRequestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private JwtUtil jwtUtil;

    @MockBean
    private CreateCancelRequestService createCancelRequestService;

    @MockBean
    private GetCancelRequestService getCancelRequestService;

    @Test
    void whenCreateCancelRequestSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");
        String token = jwtUtil.encode(username.value());

        given(createCancelRequestService.create(
                any(Username.class), any(CreateCancelRequestInput.class))).willReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/cancelRequest")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"" + orderNumber.value() + "\", " +
                                "\"content\": \"이래서 취소합니다\"" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"id\"")
                ));
    }

    @Test
    void whenUserNotExists() throws Exception {
        Username username = new Username("notExists@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");
        String token = jwtUtil.encode(username.value());

        given(createCancelRequestService.create(
                any(Username.class), any(CreateCancelRequestInput.class))).willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/cancelRequest")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"" + orderNumber.value() + "\", " +
                                "\"content\": \"이래서 취소합니다\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenOrderNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("notExists");
        String token = jwtUtil.encode(username.value());

        given(createCancelRequestService.create(
                any(Username.class), any(CreateCancelRequestInput.class))).willThrow(OrderNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/cancelRequest")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"" + orderNumber.value() + "\", " +
                                "\"content\": \"이래서 취소합니다\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenOrdererNotMatch() throws Exception {
        Username username = new Username("notTjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");
        String token = jwtUtil.encode(username.value());

        given(createCancelRequestService.create(
                any(Username.class), any(CreateCancelRequestInput.class))).willThrow(OrderNotBelongToUser.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/cancelRequest")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"" + orderNumber.value() + "\", " +
                                "\"content\": \"이래서 취소합니다\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetCancelRequestUserNotExists() throws Exception {
        Username username = new Username("notExist@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        String token = jwtUtil.encode(username.value());

        given(getCancelRequestService.getCancelRequest(
                any(Username.class), any(OrderNumber.class))).willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/cancelRequest/%s", orderNumber.value()))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenTryToGetNotExistsCancelRequest() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("notExists");

        String token = jwtUtil.encode(username.value());

        given(getCancelRequestService.getCancelRequest(
                any(Username.class), any(OrderNumber.class))).willThrow(CancelRequestNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/cancelRequest/%s", orderNumber.value()))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUserTryToGetNotHisOwnCancelRequest() throws Exception {
        Username username = new Username("notTjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        String token = jwtUtil.encode(username.value());

        given(getCancelRequestService.getCancelRequest(
                any(Username.class), any(OrderNumber.class))).willThrow(CancelRequestNotBelongToUser.class);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/cancelRequest/%s", orderNumber.value()))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenGetCancelRequestSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        String token = jwtUtil.encode(username.value());

        given(getCancelRequestService.getCancelRequest(
                any(Username.class), any(OrderNumber.class))).willReturn(CancelRequest.fake(username));

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/cancelRequest/%s", orderNumber.value()))
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\"")
                ));
    }
}
