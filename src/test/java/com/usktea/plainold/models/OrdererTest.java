package com.usktea.plainold.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
