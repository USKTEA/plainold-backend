package com.usktea.plainold.models;

import com.usktea.plainold.models.product.ProductImageUrl;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class ProductImageUrlTest {
    @Test
    void equality() {
        String url = "http://url.com";
        String otherUrl= "http://otherUrl.com";

        assertEquals(new ProductImageUrl(url), new ProductImageUrl(url));
        assertNotEquals(new ProductImageUrl(url), new ProductImageUrl(otherUrl));
    }
}
