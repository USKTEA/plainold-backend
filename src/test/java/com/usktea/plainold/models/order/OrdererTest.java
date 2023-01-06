package com.usktea.plainold.models.order;

import com.usktea.plainold.models.common.Name;
import com.usktea.plainold.models.common.PhoneNumber;
import com.usktea.plainold.models.order.Email;
import com.usktea.plainold.models.order.Orderer;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class OrdererTest {
    @Test
    void equality() {
        Name name = new Name("김뚜루");
        PhoneNumber phoneNumber = new PhoneNumber("010-2222-2222");
        Email email = new Email("tjrxo1234@gmail.com");

        Orderer orderer1 = new Orderer(name, phoneNumber, email);
        Orderer orderer2 = new Orderer(name, phoneNumber, email);

        assertThat(orderer1).isEqualTo(orderer2);
    }
}
