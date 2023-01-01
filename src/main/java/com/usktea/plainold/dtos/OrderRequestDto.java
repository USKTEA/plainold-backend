package com.usktea.plainold.dtos;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderRequestDto {
    @NotNull
    private List<OrderItemDto> orderItems;

    @NotNull
    private OrdererDto orderer;

    @NotNull
    private ShippingInformationDto shippingInformation;

    @NotNull
    private PaymentDto payment;

    @NotNull
    private ShippingFeeDto shippingFee;

    @NotNull
    private CostDto cost;

    public OrderRequestDto() {
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public OrdererDto getOrderer() {
        return orderer;
    }

    public ShippingInformationDto getShippingInformation() {
        return shippingInformation;
    }

    public PaymentDto getPayment() {
        return payment;
    }

    public ShippingFeeDto getShippingFee() {
        return shippingFee;
    }

    public CostDto getCost() {
        return cost;
    }
}
