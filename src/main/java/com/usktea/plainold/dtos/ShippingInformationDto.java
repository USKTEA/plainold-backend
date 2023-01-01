package com.usktea.plainold.dtos;

public class ShippingInformationDto {
    private ReceiverDto receiver;
    private AddressDto address;
    private String message;

    public ShippingInformationDto() {
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
