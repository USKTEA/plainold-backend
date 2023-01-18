package com.usktea.plainold.models.review;

import com.usktea.plainold.models.product.ProductId;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class ReviewTest {
    @Test
    void equality() {
        ProductId productId = new ProductId(1L);

        Review review1 = Review.fake(productId);
        Review review2 = Review.fake(productId);

        assertThat(review1).isEqualTo(review2);
    }
}
