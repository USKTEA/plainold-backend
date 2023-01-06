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

    public OrderRequestDto(List<OrderItemDto> orderItems,
                           OrdererDto orderer,
                           ShippingInformationDto shippingInformation,
                           PaymentDto payment,
                           ShippingFeeDto shippingFee,
                           CostDto cost) {
        this.orderItems = orderItems;
        this.orderer = orderer;
        this.shippingInformation = shippingInformation;
        this.payment = payment;
        this.shippingFee = shippingFee;
        this.cost = cost;
    }

    public static OrderRequestDto fake(List<OrderItemDto> orderItems) {
        OrdererDto orderer = OrdererDto.fake();
        ShippingInformationDto shippingInformation = ShippingInformationDto.fake();
        PaymentDto payment = PaymentDto.fake();
        ShippingFeeDto shippingFee = ShippingFeeDto.fake();
        CostDto cost = CostDto.fake();

        return new OrderRequestDto(orderItems, orderer, shippingInformation, payment, shippingFee, cost);
    }

    public static OrderRequestDto fake() {
        List<OrderItemDto> orderItems = List.of(OrderItemDto.fake());
        OrdererDto orderer = OrdererDto.fake();
        ShippingInformationDto shippingInformation = ShippingInformationDto.fake();
        PaymentDto payment = PaymentDto.fake();
        ShippingFeeDto shippingFee = ShippingFeeDto.fake();
        CostDto cost = CostDto.fake();

        return new OrderRequestDto(orderItems, orderer, shippingInformation, payment, shippingFee, cost);
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
