package com.usktea.plainold.models.inquiry;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class TitleTest {
    @Test
    void equality() {
        Title title1 = new Title("사이즈 문의");
        Title title2 = new Title("사이즈 문의");
        Title title3 = new Title("컬러 문의");

        assertThat(title1).isEqualTo(title2);
        assertThat(title1).isNotEqualTo(title3);
    }
}
