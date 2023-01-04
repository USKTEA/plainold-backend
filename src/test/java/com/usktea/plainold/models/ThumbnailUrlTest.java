package com.usktea.plainold.models;

import com.usktea.plainold.models.product.ThumbnailUrl;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class ThumbnailUrlTest {
    @Test
    void equality() {
        String url = "http://url.com";
        String otherUrl = "http://otherUrl.com";

        assertThat(new ThumbnailUrl(url)).isEqualTo(new ThumbnailUrl(url));
        assertThat(new ThumbnailUrl(url)).isNotEqualTo(new ThumbnailUrl(otherUrl));
    }
}
