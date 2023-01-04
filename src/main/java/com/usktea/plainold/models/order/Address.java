package com.usktea.plainold.models.order;

import com.usktea.plainold.dtos.AddressDto;
import com.usktea.plainold.dtos.ShippingAddressDto;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {
    private ZipCode zipCode;
    private Address1 address1;
    private Address2 address2;

    public Address() {
    }

    public Address(ZipCode zipCode, Address1 address1, Address2 address2) {
        this.zipCode = zipCode;
        this.address1 = address1;
        this.address2 = address2;
    }

    public static Address of(AddressDto addressDto) {
        return new Address(
                new ZipCode(addressDto.getZipCode()),
                new Address1(addressDto.getAddress1()),
                new Address2(addressDto.getAddress2())
        );
    }

    public static Address fake(ZipCode zipCode) {
        return new Address(
                zipCode,
                new Address1("서울시 성동구 상원12길 34"),
                new Address2("에이원지식산업센터")
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Address otherAddress = (Address) other;

        return Objects.equals(zipCode, otherAddress.zipCode)
                && Objects.equals(address1, otherAddress.address1)
                && Objects.equals(address2, otherAddress.address2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipCode, address1, address2);
    }

    public ShippingAddressDto toDto() {
        return new ShippingAddressDto(
                zipCode.value(),
                address1.value(),
                address2.value()
        );
    }
}
