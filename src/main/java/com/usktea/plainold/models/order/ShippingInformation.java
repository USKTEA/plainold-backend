package com.usktea.plainold.models.order;

import com.usktea.plainold.dtos.AddressDto;
import com.usktea.plainold.dtos.ReceiverDto;
import com.usktea.plainold.dtos.ShippingInformationDto;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ShippingInformation {
    private Receiver receiver;
    private Address address;
    private String message;

    public ShippingInformation() {
    }

    public ShippingInformation(Receiver receiver, Address address, String message) {
        this.receiver = receiver;
        this.address = address;
        setMessage(message);
    }

    public static ShippingInformation of(ShippingInformationDto shippingInformationDto) {
        return new ShippingInformation(
                Receiver.of(shippingInformationDto.getReceiver()),
                Address.of(shippingInformationDto.getAddress()),
                shippingInformationDto.getMessage());
    }

    public static ShippingInformation fake(Receiver receiver) {
        return new ShippingInformation(
                receiver,
                new Address(
                        new ZipCode("111111"),
                        new Address1("서울시 성동구 상원12길 34"),
                        new Address2("에이원지식산업센터")
                ),
                "빨리 와주세요");
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        ShippingInformation otherShippingInformation = (ShippingInformation) other;

        return Objects.equals(receiver, otherShippingInformation.receiver)
                && Objects.equals(address, otherShippingInformation.address)
                && Objects.equals(message, otherShippingInformation.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiver, address, message);
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public Address getAddress() {
        return address;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        if (message == null) {
            this.message = "";

            return;
        }

        this.message = message;
    }

    public ReceiverDto getReceiverDto() {
        return receiver.toDto();
    }

    public AddressDto getAddressDto() {
        return address.toDto();
    }

    public ShippingInformationDto toDto() {
        return new ShippingInformationDto(
                receiver.toDto(),
                address.toDto(),
                message
        );
    }
}
