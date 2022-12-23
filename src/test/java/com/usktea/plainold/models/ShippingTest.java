package com.usktea.plainold.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ShippingTest {

    @Test
    void equality() {
        ShippingMethod shippingMethod = ShippingMethod.Parcel;
        ShippingMethod otherShippingMethod = ShippingMethod.Post;

        Money shippingFee = new Money(2_500L);
        Money otherShippingFee = new Money(0L);

        Money freeShippingAmount = new Money(50_000L);
        Money otherFreeShippingAmount = new Money(10_000L);

        Shipping origin = new Shipping(shippingMethod, shippingFee, freeShippingAmount);

        assertAll(() -> {
            assertEquals(origin, new Shipping(shippingMethod, shippingFee, freeShippingAmount));
            assertNotEquals(origin, new Shipping(shippingMethod, shippingFee, otherFreeShippingAmount));
            assertNotEquals(origin, new Shipping(shippingMethod, otherShippingFee, freeShippingAmount));
            assertNotEquals(origin, new Shipping(otherShippingMethod, shippingFee, freeShippingAmount));
            assertNotEquals(origin, new Shipping(otherShippingMethod, otherShippingFee, otherFreeShippingAmount));
        });
    }
}
