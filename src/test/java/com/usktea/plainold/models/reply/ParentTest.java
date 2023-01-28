package com.usktea.plainold.models.reply;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class ParentTest {

    @Test
    void equality() {
        Parent parent1 = new Parent(1L);
        Parent parent2 = new Parent(1L);
        Parent parent3 = new Parent(2L);

        assertThat(parent1).isEqualTo(parent2);
        assertThat(parent1).isNotEqualTo(parent3);
    }
}
