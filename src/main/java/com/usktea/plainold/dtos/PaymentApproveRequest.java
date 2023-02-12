package com.usktea.plainold.dtos;

public class PaymentApproveRequest {
    private String provider;
    private String pgToken;
    private Integer tidId;
    private String partnerOrderId;

    public PaymentApproveRequest(String provider,
                                 String pgToken,
                                 Integer tidId,
                                 String partnerOrderId) {
        this.provider = provider;
        this.pgToken = pgToken;
        this.tidId = tidId;
        this.partnerOrderId = partnerOrderId;
    }

    public static PaymentApproveRequest of(String provider,
                                           String pgToken,
                                           Integer tidId,
                                           String partnerOrderId) {
        return new PaymentApproveRequest(
                provider,
                pgToken,
                tidId,
                partnerOrderId
        );
    }

    public Integer tidId() {
        return tidId;
    }

    public String partnerOrderId() {
        return partnerOrderId;
    }

    public String pgToken() {
        return pgToken;
    }
}
