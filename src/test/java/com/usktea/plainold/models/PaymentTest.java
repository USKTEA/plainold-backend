package com.usktea.plainold.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentTest {

    @Test
    void equality() {
        PaymentMethod paymentMethod = PaymentMethod.CASH;
        Name payer = new Name("김뚜루");

        Payment payment1 = new Payment(paymentMethod, payer);
        Payment payment2 = new Payment(paymentMethod, payer);

        assertThat(payment1).isEqualTo(payment2);
    }
}
