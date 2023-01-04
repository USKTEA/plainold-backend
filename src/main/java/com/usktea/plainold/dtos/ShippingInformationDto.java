package com.usktea.plainold.dtos;

public class ShippingInformationDto {
    private ReceiverDto receiver;
    private AddressDto address;
    private String message;

    public ShippingInformationDto() {
    }

    public ShippingInformationDto(ReceiverDto receiver, AddressDto address, String message) {
        this.receiver = receiver;
        this.address = address;
        this.message = message;
    }

    public static ShippingInformationDto fake() {
        ReceiverDto receiverDto = ReceiverDto.fake();
        AddressDto addressDto = AddressDto.fake();
        String message = "조심히 와주세요";

        return new ShippingInformationDto(receiverDto, addressDto, message);
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
