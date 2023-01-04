package com.usktea.plainold.dtos;

public class ShippingFeeDto {
    private Long amount;

    public ShippingFeeDto() {
    }

    public ShippingFeeDto(Long amount) {
        this.amount = amount;
    }

    public static ShippingFeeDto fake() {
        return new ShippingFeeDto(2_500L);
    }

    public Long getAmount() {
        return amount;
    }
}
