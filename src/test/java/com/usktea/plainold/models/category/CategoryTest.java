package com.usktea.plainold.models.category;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class CategoryTest {
    @Test
    void equality() {
        Category category1 = new Category(1L, "T-Shirt");
        Category category2 = new Category(1L, "Y-Shirt");
        Category category3 = new Category(2L, "T-Shirt");

        assertThat(category1).isEqualTo(category2);
        assertThat(category1).isNotEqualTo(category3);
    }
}
