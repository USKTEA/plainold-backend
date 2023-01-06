package com.usktea.plainold.models.order;

import com.usktea.plainold.models.common.Name;
import com.usktea.plainold.models.order.Address;
import com.usktea.plainold.models.order.Receiver;
import com.usktea.plainold.models.order.ShippingInformation;
import com.usktea.plainold.models.order.ZipCode;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
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
