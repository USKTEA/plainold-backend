package com.usktea.plainold.dtos;

import java.util.List;

public class GetOrderDetailResultDto {
    private String orderNumber;
    private List<OrderLineDto> orderLines;
    private OrdererDto orderer;
    private ShippingInformationDto shippingInformation;
    private String status;
    private Long cost;
    private Long shippingFee;
    private String payment;
    private String createdAt;

    public GetOrderDetailResultDto(OrderDetailDto orderDetail) {
        this.orderNumber = orderDetail.orderNumber();
        this.orderLines = orderDetail.orderLines();
        this.orderer = orderDetail.orderer();
        this.shippingInformation = orderDetail.shippingInformation();
        this.status = orderDetail.status();
        this.shippingFee = orderDetail.shippingFee();
        this.payment = orderDetail.payment();
        this.cost = orderDetail.cost();
        this.createdAt = orderDetail.createdAt();
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public List<OrderLineDto> getOrderLines() {
        return orderLines;
    }

    public OrdererDto getOrderer() {
        return orderer;
    }

    public ShippingInformationDto getShippingInformation() {
        return shippingInformation;
    }

    public String getStatus() {
        return status;
    }

    public Long getCost() {
        return cost;
    }

    public Long getShippingFee() {
        return shippingFee;
    }

    public String getPayment() {
        return payment;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
