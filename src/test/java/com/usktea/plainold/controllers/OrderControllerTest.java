package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.CreateOrderService;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

    @MockBean
    private CreateOrderService createOrderService;

    @BeforeEach
    void setup() {
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-202212311639");

        given(createOrderService.placeOrder(any()))
                .willReturn(Order.fake(orderNumber));
    }

    @Test
    void whenAllOrderInformationComplete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"orderItems\": [\n" +
                                "    {\n" +
                                "      \"id\": 1,\n" +
                                "      \"productId\": 1,\n" +
                                "      \"price\": 10000,\n" +
                                "      \"name\": \"T-Shirt\",\n" +
                                "      \"thumbnailUrl\": \"1\",\n" +
                                "      \"shippingFee\": 2500,\n" +
                                "      \"freeShippingAmount\": 50000,\n" +
                                "      \"quantity\": 1,\n" +
                                "      \"totalPrice\": 10000\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"orderer\": {\n" +
                                "    \"name\": \"김뚜루\",\n" +
                                "    \"phoneNumber\": \"010-5237-2189\",\n" +
                                "    \"email\": \"tjrxo1234@gmail.com\"\n" +
                                "  },\n" +
                                "  \"shippingInformation\": {\n" +
                                "    \"receiver\": { \n" +
                                "      \"name\": \"김뚜루\", \n" +
                                "      \"phoneNumber\": \"010-5237-2189\"\n" +
                                "    },\n" +
                                "    \"address\": {\n" +
                                "      \"zipCode\": \"623814\",\n" +
                                "      \"address1\": \"서울시 성동구 상원12길 34\",\n" +
                                "      \"address2\": \"에이원지식산업센터 612호\"\n" +
                                "    },\n" +
                                "    \"message\": \"빨리 와주세요\"\n" +
                                "  },\n" +
                                "  \"payment\": { \n" +
                                "    \"method\": \"CASH\", \n" +
                                "    \"payer\": \"김뚜루\"\n" +
                                "  },\n" +
                                "  \"shippingFee\": 2500,\n" +
                                "  \"cost\": 12500\n" +
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
                        .content("{\n" +
                                "  \"orderItems\": [\n" +
                                "    {\n" +
                                "      \"id\": 1,\n" +
                                "      \"productId\": 1,\n" +
                                "      \"price\": 10000,\n" +
                                "      \"name\": \"T-Shirt\",\n" +
                                "      \"thumbnailUrl\": \"1\",\n" +
                                "      \"shippingFee\": 2500,\n" +
                                "      \"freeShippingAmount\": 50000,\n" +
                                "      \"quantity\": 1,\n" +
                                "      \"totalPrice\": 10000\n" +
                                "    }\n" +
                                "  ],\n" +
                                "  \"shippingInformation\": {\n" +
                                "    \"receiver\": { \n" +
                                "      \"name\": \"김뚜루\", \n" +
                                "      \"phoneNumber\": \"010-5237-2189\"\n" +
                                "    },\n" +
                                "    \"address\": {\n" +
                                "      \"zipCode\": \"623814\",\n" +
                                "      \"address1\": \"서울시 성동구 상원12길 34\",\n" +
                                "      \"address2\": \"에이원지식산업센터 612호\"\n" +
                                "    },\n" +
                                "    \"message\": \"빨리 와주세요\"\n" +
                                "  },\n" +
                                "  \"payment\": { \n" +
                                "    \"method\": \"CASH\", \n" +
                                "    \"payer\": \"김뚜루\"\n" +
                                "  },\n" +
                                "  \"shippingFee\": 2500,\n" +
                                "  \"cost\": 12500\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }
}
