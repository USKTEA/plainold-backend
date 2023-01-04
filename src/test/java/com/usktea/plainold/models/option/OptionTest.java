package com.usktea.plainold.models.option;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class OptionTest {
    @Test
    void equality() {
        Long id = 1L;
        Long otherId = 2L;

        Option option1 = new Option(id);
        Option option2 = new Option(id);
        Option option3 = new Option(otherId);

        assertThat(option1).isEqualTo(option2);
        assertThat(option1).isNotEqualTo(option3);
    }
}
