package com.usktea.plainold.dtos;

import java.util.List;

public class PaymentReadyRequestDto {
    private String provider;
    private List<OrderItemDto> orderItems;

    public PaymentReadyRequestDto() {
    }

    public String getProvider() {
        return provider;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }
}
