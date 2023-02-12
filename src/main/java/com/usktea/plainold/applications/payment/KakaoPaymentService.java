package com.usktea.plainold.applications.payment;

import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.dtos.KakaoApproveResponse;
import com.usktea.plainold.dtos.KakaoReadyResponse;
import com.usktea.plainold.dtos.OrderItemDto;
import com.usktea.plainold.dtos.PaymentApproveRequest;
import com.usktea.plainold.dtos.PaymentReadyResponse;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.properties.KakaoPayProperties;
import com.usktea.plainold.sessions.TidSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class KakaoPaymentService implements PaymentService {
    private final KakaoPayProperties properties;
    private final TidSession tidSession;
    private final GetUserService getUserService;
    private final CalculateOrderAmountService calculateOrderAmountService;

    public KakaoPaymentService(KakaoPayProperties properties,
                               TidSession tidSession,
                               GetUserService getUserService,
                               CalculateOrderAmountService calculateOrderAmountService) {
        this.properties = properties;
        this.tidSession = tidSession;
        this.getUserService = getUserService;
        this.calculateOrderAmountService = calculateOrderAmountService;
    }

    @Override
    public PaymentReadyResponse ready(Username username, List<OrderItemDto> orderItems) {
        Users user = getUserService.find(username);

        String partnerOrderId = getPartnerOrderId();

        KakaoReadyResponse response = WebClient.create()
                .post()
                .uri(properties.getReadyUri())
                .header("Authorization", "KakaoAK " + properties.getAdminKey())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(readyRequest(user.username(), partnerOrderId, orderItems))
                .retrieve()
                .bodyToMono(KakaoReadyResponse.class)
                .block();

        Integer tidId = saveTid(response.getTid());

        return new PaymentReadyResponse(
                properties.getPaymentProvider(),
                tidId,
                partnerOrderId,
                response.getNext_redirect_pc_url());
    }

    @Override
    public String approve(Username username, PaymentApproveRequest paymentApproveRequest) {
        Users user = getUserService.find(username);
        String tid = tidSession.getTid(paymentApproveRequest.tidId());

        KakaoApproveResponse response = WebClient.create()
                .post()
                .uri(properties.getApproveUri())
                .header("Authorization", "KakaoAK " + properties.getAdminKey())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(approveRequest(user.username(), tid, paymentApproveRequest))
                .retrieve()
                .bodyToMono(KakaoApproveResponse.class)
                .block();

        return response.getAid();
    }

    private MultiValueMap<String, String> readyRequest(Username username,
                                                       String partnerOrderId,
                                                       List<OrderItemDto> orderItems) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        Integer orderAmount = calculateOrderAmountService.calculate(orderItems);

        formData.add("cid", properties.getCid());
        formData.add("partner_order_id", partnerOrderId);
        formData.add("partner_user_id", username.value());
        formData.add("item_name", getItemName(orderItems));
        formData.add("quantity", String.valueOf(getItemQuantity(orderItems)));
        formData.add("total_amount", String.valueOf(orderAmount));
        formData.add("tax_free_amount", properties.getTexFreeAmount());
        formData.add("approval_url", properties.getApprovalUrl());
        formData.add("cancel_url", properties.getCancelUrl());
        formData.add("fail_url", properties.getFailURl());

        return formData;
    }

    private MultiValueMap<String, String> approveRequest(Username username,
                                                         String tid,
                                                         PaymentApproveRequest paymentApproveRequest) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("cid", properties.getCid());
        formData.add("tid", tid);
        formData.add("partner_order_id", paymentApproveRequest.partnerOrderId());
        formData.add("partner_user_id", username.value());
        formData.add("pg_token", paymentApproveRequest.pgToken());

        return formData;
    }

    private String getItemName(List<OrderItemDto> orderItems) {
        String itemName = orderItems.get(0).getName();
        Integer itemCounts = orderItems.size();

        if (itemCounts > 1) {
            return String.format("%s 외 상품 (%s)개", itemName, itemCounts - 1);
        }

        return itemName;
    }

    private Integer getItemQuantity(List<OrderItemDto> orderItems) {
        return orderItems.size();
    }

    private String getPartnerOrderId() {
        return LocalDateTime.now().toString();
    }

    private Integer saveTid(String tid) {
        return tidSession.save(tid);
    }
}
