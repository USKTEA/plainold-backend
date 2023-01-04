package com.usktea.plainold.models;

import com.usktea.plainold.models.common.Name;
import com.usktea.plainold.models.order.Payment;
import com.usktea.plainold.models.order.PaymentMethod;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
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
