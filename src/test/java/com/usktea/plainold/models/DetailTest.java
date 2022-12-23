package com.usktea.plainold.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DetailTest {

    @Test
    void equality() {
        String content = "Very nice Shirt";
        String otherContent = "Very Good Shirt";

        assertEquals(new Detail(content), new Detail(content));
        assertNotEquals(new Detail(content), new Detail(otherContent));
    }
}
