package com.usktea.plainold.dtos;

public class PaymentReadyResponseDto {
    private String paymentProvider;
    private Integer tidId;
    private String partnerOrderId;
    private String redirectUrl;

    public PaymentReadyResponseDto() {
    }

    public PaymentReadyResponseDto(String paymentProvider,
                                   Integer tidId,
                                   String partnerOrderId,
                                   String redirectUrl) {
        this.paymentProvider = paymentProvider;
        this.tidId = tidId;
        this.partnerOrderId = partnerOrderId;
        this.redirectUrl = redirectUrl;
    }

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public Integer getTidId() {
        return tidId;
    }

    public String getPartnerOrderId() {
        return partnerOrderId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
