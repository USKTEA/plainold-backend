package com.usktea.plainold.models;

import com.usktea.plainold.models.product.ProductStatus;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class ProductStatusTest {
    @Test
    void equality() {
        assertEquals(ProductStatus.ON_SALE, ProductStatus.ON_SALE);
        assertNotEquals(ProductStatus.ON_SALE, ProductStatus.SOLD_OUT);
    }
}
