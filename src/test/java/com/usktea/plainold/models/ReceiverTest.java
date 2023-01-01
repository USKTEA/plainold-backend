package com.usktea.plainold.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
