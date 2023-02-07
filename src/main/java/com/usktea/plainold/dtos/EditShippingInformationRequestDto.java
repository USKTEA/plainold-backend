package com.usktea.plainold.dtos;

import com.sun.istack.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditShippingInformationRequestDto {
    @NotBlank
    private String orderNumber;

    @NotNull
    private ReceiverDto receiver;

    @NotNull
    private AddressDto address;

    @Nullable
    private String message;

    public EditShippingInformationRequestDto() {
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public ReceiverDto getReceiver() {
        return receiver;
    }

    public AddressDto getAddress() {
        return address;
    }

    public String getMessage() {
        return message;
    }
}
