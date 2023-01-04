package com.usktea.plainold.models;

import com.usktea.plainold.models.category.CategoryId;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class CategoryIdTest {
    @Test
    void equality() {
        Long value = 1L;
        Long otherValue = 2L;

        assertThat(new CategoryId(value)).isEqualTo(new CategoryId(value));
        assertThat(new CategoryId(value)).isNotEqualTo(new CategoryId(otherValue));
    }
}
