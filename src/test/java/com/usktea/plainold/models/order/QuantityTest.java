package com.usktea.plainold.models.order;

import com.usktea.plainold.exceptions.IncorrectQuantity;
import com.usktea.plainold.models.common.Quantity;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
class QuantityTest {
    @Test
    void creation() {
        assertDoesNotThrow(() -> new Quantity(1L));
    }

    @Test
    void whenInputIsNull() {
        assertThrows(IncorrectQuantity.class, () -> new Quantity(null));
    }

    @Test
    void whenInputIsLessThanOne() {
        assertThrows(IncorrectQuantity.class, () -> new Quantity(0L));
    }

    @Test
    void equality() {
        Quantity quantity1 = new Quantity(1L);
        Quantity quantity2 = new Quantity(1L);
        Quantity quantity3 = new Quantity(2L);

        assertThat(quantity1).isEqualTo(quantity2);
        assertThat(quantity1).isNotEqualTo(quantity3);
    }
}
