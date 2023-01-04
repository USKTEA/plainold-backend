package com.usktea.plainold.models;

import com.usktea.plainold.models.order.PaymentMethod;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
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
