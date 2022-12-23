package com.usktea.plainold.dtos;

public class ShippingDto {
    private String shippingMethod;
    private Long shippingFee;
    private Long freeShippingAmount;

    public ShippingDto() {
    }

    public ShippingDto(String shippingMethod, Long shippingFee, Long freeShippingAmount) {
        this.shippingMethod = shippingMethod;
        this.shippingFee = shippingFee;
        this.freeShippingAmount = freeShippingAmount;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public Long getShippingFee() {
        return shippingFee;
    }

    public Long getFreeShippingAmount() {
        return freeShippingAmount;
    }
}
