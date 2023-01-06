package com.usktea.plainold.models.option;

import com.usktea.plainold.exceptions.InvalidRgbValue;
import com.usktea.plainold.models.option.Rgb;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class RgbTest {
    @Test
    void creation() {
        assertDoesNotThrow(() -> new Rgb(111));
    }

    @Test
    void whenNumberBelowZero() {
        assertThrows(InvalidRgbValue.class, () -> new Rgb(-1));
    }

    @Test
    void whenNumberBiggerThan255() {
        assertThrows(InvalidRgbValue.class, () -> new Rgb(256));
    }

    @Test
    void equality() {
        Rgb rgb1 = new Rgb(1);
        Rgb rgb2 = new Rgb(1);
        Rgb rgb3 = new Rgb(2);

        assertThat(rgb1).isEqualTo(rgb2);
        assertThat(rgb2).isNotEqualTo(rgb3);
    }
}
