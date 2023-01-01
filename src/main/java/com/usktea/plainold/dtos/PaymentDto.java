package com.usktea.plainold.dtos;

public class PaymentDto {
    private String method;
    private String payer;

    public PaymentDto() {
    }

    public String getMethod() {
        return method;
    }

    public String getPayer() {
        return payer;
    }
}
