package com.usktea.plainold.models.review;

import com.usktea.plainold.exceptions.InvalidRate;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
class RateTest {
    @Test
    void equality() {
        Rate five = new Rate(5);
        Rate otherFive = new Rate(5);
        Rate four = new Rate(4);

        assertThat(five).isEqualTo(otherFive);
        assertThat(five).isNotEqualTo(four);
    }

    @Test
    void whenRateIsHigherThan5() {
        assertThrows(InvalidRate.class, () -> new Rate(6));
    }

    @Test
    void whenRateIsLowerThan1() {
        assertThrows(InvalidRate.class, () -> new Rate(0));
    }
}
