package com.usktea.plainold.models.review;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class ReviewImageUrlTest {

    @Test
    void equality() {
        ReviewImageUrl reviewImageUrl1 = new ReviewImageUrl("1");
        ReviewImageUrl reviewImageUrl2 = new ReviewImageUrl("1");
        ReviewImageUrl reviewImageUrl3 = new ReviewImageUrl("2");

        assertThat(reviewImageUrl1).isEqualTo(reviewImageUrl2);
        assertThat(reviewImageUrl1).isNotEqualTo(reviewImageUrl3);
    }
}
