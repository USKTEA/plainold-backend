package com.usktea.plainold.dtos;

public class PaymentReadyResponse {
    private String paymentProvider;
    private Integer tidId;
    private String partnerOrderId;
    private String redirectUrl;

    public PaymentReadyResponse() {
    }

    public PaymentReadyResponse(String paymentProvider,
                                Integer tidId,
                                String partnerOrderId,
                                String next_redirect_pc_url) {
        this.paymentProvider = paymentProvider;
        this.tidId = tidId;
        this.partnerOrderId = partnerOrderId;
        this.redirectUrl = next_redirect_pc_url;
    }

    public static PaymentReadyResponse fake(Integer tidId) {
        return new PaymentReadyResponse(
                "KAKAOPAY",
                tidId,
                "1",
                "1"
        );
    }

    public PaymentReadyResponseDto toDto() {
        return new PaymentReadyResponseDto(
                paymentProvider,
                tidId,
                partnerOrderId,
                redirectUrl
        );
    }
}
