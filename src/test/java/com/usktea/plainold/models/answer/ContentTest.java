package com.usktea.plainold.models.answer;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class ContentTest {
    @Test
    void equality() {
        Content content1 = new Content("맞습니다");
        Content content2 = new Content("맞습니다");
        Content content3 = new Content("아닙니다");

        assertThat(content1).isEqualTo(content2);
        assertThat(content1).isNotEqualTo(content3);
    }
}
