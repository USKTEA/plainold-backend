package com.usktea.plainold.models;

import com.usktea.plainold.exceptions.InvalidProductPrice;
import com.usktea.plainold.exceptions.ProductSoldOut;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
class ProductTest {
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

    @Test
    void productIsSoldOut() {
        Product soldOut = Product.fake(ProductStatus.SOLD_OUT);

        assertThrows(ProductSoldOut.class, () -> soldOut.checkIsSoldOut());
    }
}
