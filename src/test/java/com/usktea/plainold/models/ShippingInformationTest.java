package com.usktea.plainold.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShippingInformationTest {

    @Test
    void equality() {
        Name name = new Name("김뚜루");
        ZipCode zipCode = new ZipCode("111111");

        Receiver receiver = Receiver.fake(name);
        Address address = Address.fake(zipCode);
        String message = "부재시 경비실에 맡겨주세요.";

        ShippingInformation shippingInformation1 = new ShippingInformation(receiver, address, message);
        ShippingInformation shippingInformation2 = new ShippingInformation(receiver, address, message);

        assertThat(shippingInformation1).isEqualTo(shippingInformation2);
    }
}
