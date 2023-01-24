package com.usktea.plainold.models.review;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class ImageUrlTest {

    @Test
    void equality() {
        ImageUrl imageUrl1 = new ImageUrl("1");
        ImageUrl imageUrl2 = new ImageUrl("1");
        ImageUrl imageUrl3 = new ImageUrl("2");

        assertThat(imageUrl1).isEqualTo(imageUrl2);
        assertThat(imageUrl1).isNotEqualTo(imageUrl3);
    }
}
