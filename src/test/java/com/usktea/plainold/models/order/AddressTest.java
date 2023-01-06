package com.usktea.plainold.models.order;

import com.usktea.plainold.models.order.Address;
import com.usktea.plainold.models.order.Address1;
import com.usktea.plainold.models.order.Address2;
import com.usktea.plainold.models.order.ZipCode;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
class AddressTest {

    @Test
    void equality() {
        ZipCode zipCode = new ZipCode("111111");
        Address1 address1 = new Address1("서울시 성동구 상원12길 34");
        Address2 address2 = new Address2("에이원지식산업센터");

        Address address = new Address(zipCode, address1, address2);
        Address sameAddress = new Address(zipCode, address1, address2);

        assertThat(address).isEqualTo(sameAddress);
    }
}
