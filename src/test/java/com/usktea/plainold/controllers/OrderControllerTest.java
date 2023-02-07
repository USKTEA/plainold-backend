package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.order.CancelOrderService;
import com.usktea.plainold.applications.order.EditOrderService;
import com.usktea.plainold.applications.order.CreateOrderService;
import com.usktea.plainold.applications.order.GetOrderCanWriteReviewService;
import com.usktea.plainold.applications.order.GetOrderDetailService;
import com.usktea.plainold.applications.order.GetUserOrderService;
import com.usktea.plainold.dtos.EditOrderRequest;
import com.usktea.plainold.dtos.OrderDetailDto;
import com.usktea.plainold.exceptions.OrderCanWriteReviewNotFound;
import com.usktea.plainold.exceptions.OrderCannotBeEdited;
import com.usktea.plainold.exceptions.OrderNotBelongToUser;
import com.usktea.plainold.exceptions.OrderNotFound;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@ActiveProfiles("test")
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private JwtUtil jwtUtil;

    @MockBean
    private CreateOrderService createOrderService;

    @MockBean
    private GetOrderCanWriteReviewService getOrderCanWriteReviewService;

    @MockBean
    private GetUserOrderService getUserOrderService;

    @MockBean
    private GetOrderDetailService getOrderDetailService;

    @MockBean
    private EditOrderService editOrderService;

    @MockBean
    private CancelOrderService cancelOrderService;

    @BeforeEach
    void setup() {
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-202212311639");

        given(createOrderService.placeOrder(any()))
                .willReturn(Order.fake(orderNumber));
    }

    @Test
    void whenAllOrderInformationComplete() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");

        String token = jwtUtil.encode(username.value());

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "  \"orderItems\": [" +
                                "    {" +
                                "      \"id\": 1," +
                                "      \"productId\": 1," +
                                "      \"price\": 10000," +
                                "      \"name\": \"T-Shirt\"," +
                                "      \"thumbnailUrl\": \"1\"," +
                                "      \"shippingFee\": 2500," +
                                "      \"freeShippingAmount\": 50000," +
                                "      \"quantity\": 1," +
                                "      \"totalPrice\": 10000" +
                                "    }" +
                                "  ]," +
                                "  \"orderer\": {" +
                                "    \"name\": \"김뚜루\"," +
                                "    \"phoneNumber\": \"010-5237-2189\"," +
                                "    \"email\": \"tjrxo1234@gmail.com\"" +
                                "  }," +
                                "  \"shippingInformation\": {" +
                                "    \"receiver\": { " +
                                "      \"name\": \"김뚜루\", " +
                                "      \"phoneNumber\": \"010-5237-2189\"" +
                                "    }," +
                                "    \"address\": {" +
                                "      \"zipCode\": \"623814\"," +
                                "      \"address1\": \"서울시 성동구 상원12길 34\"," +
                                "      \"address2\": \"에이원지식산업센터 612호\"" +
                                "    }," +
                                "    \"message\": \"빨리 와주세요\"" +
                                "  }," +
                                "  \"payment\": { " +
                                "    \"method\": \"CASH\", " +
                                "    \"payer\": \"김뚜루\"" +
                                "  }," +
                                "  \"shippingFee\": 2500," +
                                "  \"cost\": 12500" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"orderNumber\":")
                ));
    }

    @Test
    void whenNotReceiveSumOfOrderRequestInformation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "  \"orderItems\": [" +
                                "    {" +
                                "      \"id\": 1," +
                                "      \"productId\": 1," +
                                "      \"price\": 10000," +
                                "      \"name\": \"T-Shirt\"," +
                                "      \"thumbnailUrl\": \"1\"," +
                                "      \"shippingFee\": 2500," +
                                "      \"freeShippingAmount\": 50000," +
                                "      \"quantity\": 1," +
                                "      \"totalPrice\": 10000" +
                                "    }" +
                                "  ]," +
                                "  \"shippingInformation\": {" +
                                "    \"receiver\": { " +
                                "      \"name\": \"김뚜루\", " +
                                "      \"phoneNumber\": \"010-5237-2189\"" +
                                "    }," +
                                "    \"address\": {" +
                                "      \"zipCode\": \"623814\"," +
                                "      \"address1\": \"서울시 성동구 상원12길 34\"," +
                                "      \"address2\": \"에이원지식산업센터 612호\"" +
                                "    }," +
                                "    \"message\": \"빨리 와주세요\"" +
                                "  }," +
                                "  \"payment\": { " +
                                "    \"method\": \"CASH\", " +
                                "    \"payer\": \"김뚜루\"" +
                                "  }," +
                                "  \"shippingFee\": 2500," +
                                "  \"cost\": 12500" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFindOrderUserNotExists() throws Exception {
        ProductId productId = new ProductId(1L);
        Username username = new Username("tjrxo1234@gmail.com");

        String token = jwtUtil.encode(username.value());

        given(getOrderCanWriteReviewService.order(username, productId))
                .willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/orders?productId=%d", productId.value()))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenProductIdNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        mockMvc.perform(MockMvcRequestBuilders.get("/orders?productId=")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFindOrderProductNotExists() throws Exception {
        ProductId productId = new ProductId(9_999_999L);
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        given(getOrderCanWriteReviewService.order(username, productId))
                .willThrow(ProductNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/orders?productId=%d", productId.value()))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDoNotHaveOrderCanWriteReview() throws Exception {
        ProductId productId = new ProductId(2L);
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        given(getOrderCanWriteReviewService.order(username, productId))
                .willThrow(OrderCanWriteReviewNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/orders?productId=%d", productId.value()))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void findNotReviewedOrderSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(1L);
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-202301061131");
        String token = jwtUtil.encode(username.value());

        given(getOrderCanWriteReviewService.order(username, productId))
                .willReturn(Order.fake(orderNumber));

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/orders?productId=%d", productId.value()))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"orderNumber\"")
                ));
    }

    @Test
    void findUserOrdersSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(getUserOrderService.orders(any(Username.class)))
                .willReturn(List.of(Order.fake(orderNumber)));

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"orders\"")
                ));
    }

    @Test
    void whenTryToFindNotExistsUserOrder() throws Exception {
        Username username = new Username("notExists@gmail.com");
        String token = jwtUtil.encode(username.value());

        given(getUserOrderService.orders(any(Username.class)))
                .willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void whenUserOrdersNotExists() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());

        given(getUserOrderService.orders(any(Username.class)))
                .willReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenGetOrderSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-1111111");

        given(getOrderDetailService.getOrder(username, orderNumber))
                .willReturn(OrderDetailDto.fake(orderNumber));

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/orders/%s", orderNumber.value()))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"orderNumber\"")
                ));
    }

    @Test
    void whenGetOrderUserNotExists() throws Exception {
        Username username = new Username("notExists@gmail.com");
        String token = jwtUtil.encode(username.value());
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(getOrderDetailService.getOrder(username, orderNumber))
                .willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/orders/%s", orderNumber.value()))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUserTryToGetNotHisOwnOrder() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(getOrderDetailService.getOrder(username, orderNumber))
                .willThrow(OrderNotBelongToUser.class);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/orders/%s", orderNumber.value()))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenEditOrderUserNotExists() throws Exception {
        Username username = new Username("notExists@gmail.com");
        String token = jwtUtil.encode(username.value());
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(editOrderService.edit(any(Username.class), any(EditOrderRequest.class)))
                .willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/shippingInformation")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"" + orderNumber.value() + "\", " +
                                "\"receiver\": { " +
                                "\"name\": \"김뚜루\", " +
                                "\"phoneNumber\": \"010-5237-2189\"" +
                                "}," +
                                "\"address\": {" +
                                "\"zipCode\": \"623814\"," +
                                "\"address1\": \"서울시 성동구 상원12길 34\"," +
                                "\"address2\": \"에이원지식산업센터 612호\"" +
                                "}," +
                                "\"message\": \"빨리 와주세요\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUserTryToEditNotHisOwnOrder() throws Exception {
        Username username = new Username("notTjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(editOrderService.edit(any(Username.class), any(EditOrderRequest.class)))
                .willThrow(OrderNotBelongToUser.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/shippingInformation")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"" + orderNumber.value() + "\", " +
                                "\"receiver\": { " +
                                "\"name\": \"김뚜루\", " +
                                "\"phoneNumber\": \"010-5237-2189\"" +
                                "}," +
                                "\"address\": {" +
                                "\"zipCode\": \"623814\"," +
                                "\"address1\": \"서울시 성동구 상원12길 34\"," +
                                "\"address2\": \"에이원지식산업센터 612호\"" +
                                "}," +
                                "\"message\": \"빨리 와주세요\"" +
                                "}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenSomeOfEditOrderRequestInformationIncomplete() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/shippingInformation")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"" + orderNumber.value() + "\", " +
                                "\"receiver\": { " +
                                "\"name\": \"\", " +
                                "\"phoneNumber\": \"010-5237-2189\"" +
                                "}," +
                                "\"address\": {" +
                                "\"zipCode\": \"623814\"," +
                                "\"address1\": \"서울시 성동구 상원12길 34\"," +
                                "\"address2\": \"에이원지식산업센터 612호\"" +
                                "}," +
                                "\"message\": \"빨리 와주세요\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenTryToEditNotExistsOrder() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(editOrderService.edit(any(Username.class), any(EditOrderRequest.class)))
                .willThrow(OrderNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/shippingInformation")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"" + orderNumber.value() + "\", " +
                                "\"receiver\": { " +
                                "\"name\": \"김뚜루\", " +
                                "\"phoneNumber\": \"010-5237-2189\"" +
                                "}," +
                                "\"address\": {" +
                                "\"zipCode\": \"623814\"," +
                                "\"address1\": \"서울시 성동구 상원12길 34\"," +
                                "\"address2\": \"에이원지식산업센터 612호\"" +
                                "}," +
                                "\"message\": \"빨리 와주세요\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenOrderCannotBeEdited() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(editOrderService.edit(any(Username.class), any(EditOrderRequest.class)))
                .willThrow(OrderCannotBeEdited.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/shippingInformation")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"" + orderNumber.value() + "\", " +
                                "\"receiver\": { " +
                                "\"name\": \"김뚜루\", " +
                                "\"phoneNumber\": \"010-5237-2189\"" +
                                "}," +
                                "\"address\": {" +
                                "\"zipCode\": \"623814\"," +
                                "\"address1\": \"서울시 성동구 상원12길 34\"," +
                                "\"address2\": \"에이원지식산업센터 612호\"" +
                                "}," +
                                "\"message\": \"빨리 와주세요\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenEditOrderSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String token = jwtUtil.encode(username.value());
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");

        given(editOrderService.edit(any(Username.class), any(EditOrderRequest.class)))
                .willReturn(orderNumber);

        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/shippingInformation")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"" + orderNumber.value() + "\", " +
                                "\"receiver\": { " +
                                "\"name\": \"김뚜루\", " +
                                "\"phoneNumber\": \"010-5237-2189\"" +
                                "}," +
                                "\"address\": {" +
                                "\"zipCode\": \"623814\"," +
                                "\"address1\": \"서울시 성동구 상원12길 34\"," +
                                "\"address2\": \"에이원지식산업센터 612호\"" +
                                "}," +
                                "\"message\": \"빨리 와주세요\"" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"orderNumber\"")
                ));
    }

    @Test
    void whenUserNotExists() throws Exception {
        Username username = new Username("notExists@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");
        String token = jwtUtil.encode(username.value());

        given(cancelOrderService.cancel(any(Username.class), any(OrderNumber.class)))
                .willThrow(UserNotExists.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/orderStatus")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"" + orderNumber.value() + "\", " +
                                "\"cancel\": true" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUserTryToCancelNotHisOwnOrder() throws Exception {
        Username username = new Username("notTjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-11111111");
        String token = jwtUtil.encode(username.value());

        given(cancelOrderService.cancel(any(Username.class), any(OrderNumber.class)))
                .willThrow(OrderNotBelongToUser.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/orderStatus")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"" + orderNumber.value() + "\", " +
                                "\"cancel\": true" +
                                "}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenCancelOrderOrderNotExists() throws Exception {
        Username username = new Username("notTjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("notExists");
        String token = jwtUtil.encode(username.value());

        given(cancelOrderService.cancel(any(Username.class), any(OrderNumber.class)))
                .willThrow(OrderNotFound.class);

        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/orderStatus")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"" + orderNumber.value() + "\", " +
                                "\"cancel\": true" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCancelOrderRequestInformationIsNotTrue() throws Exception {
        Username username = new Username("notTjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("notExists");
        String token = jwtUtil.encode(username.value());

        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/orderStatus")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"" + orderNumber.value() + "\", " +
                                "\"cancel\": false" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCancelOrderSuccess() throws Exception {
        Username username = new Username("notTjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("notExists");
        String token = jwtUtil.encode(username.value());

        given(cancelOrderService.cancel(any(Username.class), any(OrderNumber.class)))
                .willReturn(orderNumber);

        mockMvc.perform(MockMvcRequestBuilders.patch("/orders/orderStatus")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"orderNumber\": \"" + orderNumber.value() + "\", " +
                                "\"cancel\": true" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"orderNumber\"")
                ));
    }
}
