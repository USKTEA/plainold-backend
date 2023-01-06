package com.usktea.plainold.models.product;

import com.usktea.plainold.models.product.Detail;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ActiveProfiles("test")
class DetailTest {
    @Test
    void equality() {
        String content = "Very nice Shirt";
        String otherContent = "Very Good Shirt";

        assertEquals(new Detail(content), new Detail(content));
        assertNotEquals(new Detail(content), new Detail(otherContent));
    }
}
