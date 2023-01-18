package com.usktea.plainold.dtos;

import com.usktea.plainold.models.order.OrderNumber;

public class OrderNumberDto {
    private String orderNumber;

    public OrderNumberDto() {
    }

    public OrderNumberDto(OrderNumber orderNumber) {
        this.orderNumber = orderNumber.value();
    }

    public String getOrderNumber() {
        return orderNumber;
    }
}
