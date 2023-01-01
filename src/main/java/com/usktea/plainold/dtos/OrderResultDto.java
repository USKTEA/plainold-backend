package com.usktea.plainold.dtos;

import com.usktea.plainold.models.Money;
import com.usktea.plainold.models.OrderNumber;
import com.usktea.plainold.models.ShippingInformation;

public class OrderResultDto {
    private String orderNumber;
    private Long cost;
    private ReceiverDto receiver;
    private ShippingAddressDto shippingAddress;

    public OrderResultDto() {
    }

    public OrderResultDto(OrderNumber orderNumber,
                          Money cost,
                          ShippingInformation shippingInformation) {
        this.orderNumber = orderNumber.value();
        this.cost = cost.amount();
        this.receiver = shippingInformation.getReceiver().toDto();
        this.shippingAddress = shippingInformation.getAddress().toDto();
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Long getCost() {
        return cost;
    }

    public ReceiverDto getReceiver() {
        return receiver;
    }

    public ShippingAddressDto getShippingAddress() {
        return shippingAddress;
    }
}
