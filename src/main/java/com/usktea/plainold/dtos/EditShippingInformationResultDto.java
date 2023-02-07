package com.usktea.plainold.dtos;

public class EditShippingInformationResultDto {
    private String orderNumber;

    public EditShippingInformationResultDto() {
    }

    public EditShippingInformationResultDto(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }
}
