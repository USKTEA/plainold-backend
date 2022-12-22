package com.usktea.plainold.models;

import com.usktea.plainold.exceptions.InvalidPrice;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class PriceTest {
    @Test
    void whenAmountBelowZero() {
        assertThrows(InvalidPrice.class, () -> {
            new Price(-1L);
        });
    }

    @Test
    void whenAmountNotBelowZero() {
        assertDoesNotThrow(() -> {
            new Price(1L);
        });
    }

    @Test
    void equality() {
        assertThat(new Price(1_000L)).isEqualTo(new Price(1_000L));
        assertThat(new Price(1_000L)).isNotEqualTo(new Price(2_000L));
    }
}
