package com.usktea.plainold.models.option;

import com.usktea.plainold.models.option.Color;
import com.usktea.plainold.models.option.Rgb;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class ColorTest {
    @Test
    void equality() {
        Color red1 = new Color("red", new Rgb(255), new Rgb(0), new Rgb(0));
        Color red2 = new Color("red", new Rgb(255), new Rgb(0), new Rgb(0));
        Color blue = new Color("blue", new Rgb(0), new Rgb(0), new Rgb(255));

        assertThat(red1).isEqualTo(red2);
        assertThat(red1).isNotEqualTo(blue);
    }
}
