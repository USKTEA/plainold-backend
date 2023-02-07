package com.usktea.plainold.dtos;

public class CancelOrderResultDto {
    private String orderNumber;

    public CancelOrderResultDto() {
    }

    public CancelOrderResultDto(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }
}
