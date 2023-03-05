package com.usktea.plainold.controllers;

import com.usktea.plainold.applications.payment.PaymentService;
import com.usktea.plainold.applications.payment.PaymentServiceFactory;
import com.usktea.plainold.dtos.PaymentApproveRequest;
import com.usktea.plainold.dtos.PaymentReadyResponse;
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
@WebMvcTest(PaymentController.class)
class PaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private JwtUtil jwtUtil;

    @MockBean
    private PaymentServiceFactory paymentServiceFactory;

    @MockBean
    private PaymentService paymentService;

    @Test
    void whenGetReadySuccess() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String provider = "KAKAOPAY";

        String token = jwtUtil.encode(username.value());
        Integer tidId = 1;

        given(paymentService.ready(any(), any()))
                .willReturn(PaymentReadyResponse.fake(tidId));

        given(paymentServiceFactory.getmentPayService(any()))
                .willReturn(paymentService);

        mockMvc.perform(MockMvcRequestBuilders.post("/payments")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"provider\": \"" + provider + "\" ," +
                                "\"orderItems\": [" +
                                "{" +
                                "\"id\": 1," +
                                "\"productId\": 1," +
                                "\"price\": 10000," +
                                "\"name\": \"T-Shirt\"," +
                                "\"thumbnailUrl\": \"1\"," +
                                "\"shippingFee\": 2500," +
                                "\"freeShippingAmount\": 50000," +
                                "\"quantity\": 1," +
                                "\"totalPrice\": 10000" +
                                "}" +
                                "]" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        containsString("\"tidId\"")
                ));
    }

    @Test
    void whenGetReadyFailed() throws Exception {
        Username username = new Username("tjrxo1234@gmail.com");
        String provider = "INVALID";

        String token = jwtUtil.encode(username.value());

        given(paymentServiceFactory.getmentPayService(any()))
                .willReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/payments")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"provider\": \"" + provider + "\" ," +
                                "\"orderItems\": [" +
                                "{" +
                                "\"id\": 1," +
                                "\"productId\": 1," +
                                "\"price\": 10000," +
                                "\"name\": \"T-Shirt\"," +
                                "\"thumbnailUrl\": \"1\"," +
                                "\"shippingFee\": 2500," +
                                "\"freeShippingAmount\": 50000," +
                                "\"quantity\": 1," +
                                "\"totalPrice\": 10000" +
                                "}" +
                                "]" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetApproveSuccess() throws Exception {
        Username username = new Username("tjrxo1234@gamil.com");
        String token = jwtUtil.encode(username.value());

        String provider = "KAKAOPAY";
        String pgToken = "pgToken";
        String tidId = "1";
        String partnerOrderId = "1";

        given(paymentServiceFactory.getmentPayService(any())).willReturn(paymentService);
        given(paymentService.approve(any(Username.class), any(PaymentApproveRequest.class)))
                .willReturn("1");

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/payments?provider=%s&pgToken=%s&tidId=%s&partnerOrderId=%s",
                                provider, pgToken, tidId, partnerOrderId))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"approveCode\"")
                ));
    }

    @Test
    void whenGetApproveFailed() throws Exception {
        Username username = new Username("tjrxo1234@gamil.com");
        String token = jwtUtil.encode(username.value());

        String provider = "INVALID";
        String pgToken = "pgToken";
        String tidId = "1";
        String partnerOrderId = "1";

        given(paymentServiceFactory.getmentPayService(any())).willReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/payments?provider=%s&pgToken=%s&tidId=%s&partnerOrderId=%s",
                                provider, pgToken, tidId, partnerOrderId))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }
}
