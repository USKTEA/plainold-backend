package com.usktea.plainold.models;

import com.usktea.plainold.models.product.ShippingMethod;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class ShippingMethodTest {
    @Test
    void equality() {
        assertEquals(ShippingMethod.Parcel, ShippingMethod.Parcel);
        assertNotEquals(ShippingMethod.Parcel, ShippingMethod.Post);
    }
}
