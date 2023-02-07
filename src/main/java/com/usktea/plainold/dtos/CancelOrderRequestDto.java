package com.usktea.plainold.dtos;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;

public class CancelOrderRequestDto {
    @NotEmpty
    private String orderNumber;

    @AssertTrue
    private Boolean cancel;

    public CancelOrderRequestDto() {
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Boolean getCancel() {
        return cancel;
    }
}
