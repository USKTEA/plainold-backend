package com.usktea.plainold.models;

import com.usktea.plainold.models.product.Image;
import com.usktea.plainold.models.product.ProductImageUrl;
import com.usktea.plainold.models.product.ThumbnailUrl;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class ImageTest {
    @Test
    void equality() {
        ThumbnailUrl thumbnailUrl = new ThumbnailUrl("http://thumbnailUrl.com");
        ThumbnailUrl otherThumbnailUrl = new ThumbnailUrl("http://otherThumbnailUrl.com");

        Set<ProductImageUrl> productImageUrls = Set.of(new ProductImageUrl("http://productImageUrl.com"));
        Set<ProductImageUrl> otherProductImageUrls = Set.of(new ProductImageUrl("http://otherProductImageUrl.com"));

        Image origin = new Image(thumbnailUrl, productImageUrls);

        assertEquals(origin, new Image(thumbnailUrl, productImageUrls));

        assertNotEquals(origin, new Image(otherThumbnailUrl, productImageUrls));
        assertNotEquals(origin, new Image(thumbnailUrl, otherProductImageUrls));
        assertNotEquals(origin, new Image(otherThumbnailUrl, otherProductImageUrls));
    }
}
