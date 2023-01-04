package com.usktea.plainold.models;

import com.usktea.plainold.models.product.Description;
import com.usktea.plainold.models.product.Detail;
import com.usktea.plainold.models.product.Summary;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ActiveProfiles("test")
class DescriptionTest {
    @Test
    void equality() {
        Summary summary = new Summary("Good Shirt");
        Summary otherSummary = new Summary("Nice Shirt");

        Detail detail = new Detail("Very Good Shirt");
        Detail otherDetail = new Detail("Very Nice Shirt");

        Description origin = new Description(summary, detail);

        assertEquals(origin, new Description(summary, detail));

        assertNotEquals(origin, new Description(summary, otherDetail));
        assertNotEquals(origin, new Description(otherSummary, detail));
        assertNotEquals(origin, new Description(otherSummary, otherDetail));
    }
}
