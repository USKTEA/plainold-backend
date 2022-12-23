package com.usktea.plainold.models;

import com.usktea.plainold.exceptions.InvalidProductPrice;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
class ProductTest {
//TODO 제대로 Product이 나오면 creation 테스트 작성
    @Test
    void equality() {
        Long id = 1L;
        Long otherId = 2L;

        assertThat(Product.fake(id)).isEqualTo(Product.fake(id));
        assertThat(Product.fake(id)).isNotEqualTo(Product.fake(otherId));
    }

    @Test
    void whenPriceIsLowerThanZero() {
        assertThrows(InvalidProductPrice.class, () -> Product.fake(new Money(-1L)));
    }
}
