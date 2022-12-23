package com.usktea.plainold.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShippingMethodTest {

    @Test
    void equality() {
        assertEquals(ShippingMethod.Parcel, ShippingMethod.Parcel);
        assertNotEquals(ShippingMethod.Parcel, ShippingMethod.Post);
    }
}
