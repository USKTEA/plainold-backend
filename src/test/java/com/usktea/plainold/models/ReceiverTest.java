package com.usktea.plainold.models;

import com.usktea.plainold.models.common.Name;
import com.usktea.plainold.models.common.PhoneNumber;
import com.usktea.plainold.models.order.Receiver;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class ReceiverTest {
    @Test
    void equality() {
        Name name = new Name("김뚜루");
        PhoneNumber phoneNumber = new PhoneNumber("010-1111-1111");

        Receiver receiver1 = new Receiver(name, phoneNumber);
        Receiver receiver2 = new Receiver(name, phoneNumber);

        assertThat(receiver1).isEqualTo(receiver2);
    }
}
