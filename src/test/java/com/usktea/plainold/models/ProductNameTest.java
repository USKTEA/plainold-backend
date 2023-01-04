package com.usktea.plainold.models;

import com.usktea.plainold.models.product.ProductName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class ProductNameTest {
    @Test
    void equality() {
        assertThat(new ProductName("T-shirt")).isEqualTo(new ProductName("T-shirt"));
        assertThat(new ProductName("T-shirt")).isNotEqualTo(new ProductName("Jean"));
    }
}
