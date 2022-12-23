package com.usktea.plainold.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SummaryTest {

    @Test
    void equality() {
        String content = "Pretty Shirt";
        String otherContent = "Good Shirt";

        assertEquals(new Summary(content), new Summary(content));
        assertNotEquals(new Summary(content), new Summary(otherContent));
    }
}
