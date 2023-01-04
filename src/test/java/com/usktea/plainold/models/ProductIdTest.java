package com.usktea.plainold.models;

import com.usktea.plainold.models.product.ProductId;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class ProductIdTest {
    @Test
    void equality() {
        ProductId productId1 = new ProductId(1L);
        ProductId productId2 = new ProductId(1L);
        ProductId productId3 = new ProductId(2L);

        assertThat(productId1).isEqualTo(productId2);
        assertThat(productId1).isNotEqualTo(productId3);
     }
}
