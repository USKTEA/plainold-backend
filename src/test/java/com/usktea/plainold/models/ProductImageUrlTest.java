package com.usktea.plainold.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductImageUrlTest {

    @Test
    void equality() {
        String url = "http://url.com";
        String otherUrl= "http://otherUrl.com";

        assertEquals(new ProductImageUrl(url), new ProductImageUrl(url));
        assertNotEquals(new ProductImageUrl(url), new ProductImageUrl(otherUrl));
    }
}
