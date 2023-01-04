package com.usktea.plainold.models;

import com.usktea.plainold.models.product.Summary;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class SummaryTest {
    @Test
    void equality() {
        String content = "Pretty Shirt";
        String otherContent = "Good Shirt";

        assertEquals(new Summary(content), new Summary(content));
        assertNotEquals(new Summary(content), new Summary(otherContent));
    }
}
