package com.usktea.plainold.dtos;

public class PaymentDto {
    private String method;
    private String payer;

    public PaymentDto() {
    }

    public PaymentDto(String method, String payer) {
        this.method = method;
        this.payer = payer;
    }

    public static PaymentDto fake() {
        return new PaymentDto("CASH", "김두루");
    }

    public String getMethod() {
        return method;
    }

    public String getPayer() {
        return payer;
    }
}
