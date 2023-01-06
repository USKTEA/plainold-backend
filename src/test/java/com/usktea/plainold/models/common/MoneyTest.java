package com.usktea.plainold.models.common;

import com.usktea.plainold.models.common.Money;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class MoneyTest {
    @Test
    void equality() {
        assertThat(new Money(1_000L)).isEqualTo(new Money(1_000L));
        assertThat(new Money(1_000L)).isNotEqualTo(new Money(2_000L));
    }
}
