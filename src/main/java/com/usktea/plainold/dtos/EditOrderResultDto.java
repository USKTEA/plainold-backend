package com.usktea.plainold.dtos;

public class EditOrderResultDto {
    private String orderNumber;

    public EditOrderResultDto() {
    }

    public EditOrderResultDto(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }
}
