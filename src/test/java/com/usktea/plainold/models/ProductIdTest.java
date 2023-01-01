package com.usktea.plainold.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
