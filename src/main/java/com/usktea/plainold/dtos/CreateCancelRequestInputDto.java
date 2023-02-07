package com.usktea.plainold.dtos;

import javax.validation.constraints.NotEmpty;

public class CreateCancelRequestInputDto {
    @NotEmpty
    private String orderNumber;

    private String content;

    public CreateCancelRequestInputDto() {
    }

    public CreateCancelRequestInputDto(String orderNumber, String content) {
        this.orderNumber = orderNumber;
        this.content = content;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getContent() {
        return content;
    }
}
