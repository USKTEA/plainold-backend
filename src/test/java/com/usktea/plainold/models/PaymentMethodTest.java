package com.usktea.plainold.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentMethodTest {

    @Test
    void equality() {
        PaymentMethod method1 = PaymentMethod.CASH;
        PaymentMethod method2 = PaymentMethod.CASH;
        PaymentMethod method3 = PaymentMethod.KAKAOPAY;

        assertThat(method1).isEqualTo(method2);
        assertThat(method1).isNotEqualTo(method3);
    }
}
