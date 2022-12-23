package com.usktea.plainold.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductStatusTest {

    @Test
    void equality() {
        assertEquals(ProductStatus.ON_SALE, ProductStatus.ON_SALE);
        assertNotEquals(ProductStatus.ON_SALE, ProductStatus.SOLD_OUT);
    }
}
