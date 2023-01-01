package com.usktea.plainold.dtos;

public class ShippingFeeDto {
    private Long amount;

    public ShippingFeeDto() {
    }

    public ShippingFeeDto(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }
}
