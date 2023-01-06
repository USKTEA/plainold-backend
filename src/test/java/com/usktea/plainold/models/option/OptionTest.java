package com.usktea.plainold.models.option;

import com.usktea.plainold.exceptions.InvalidProductOption;
import com.usktea.plainold.models.product.ProductId;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
class OptionTest {
    @Test
    void equality() {
        Long id = 1L;
        Long otherId = 2L;

        Option option1 = new Option(id);
        Option option2 = new Option(id);
        Option option3 = new Option(otherId);

        assertThat(option1).isEqualTo(option2);
        assertThat(option1).isNotEqualTo(option3);
    }

    @Test
    void whenCheckIsValidPass() {
        ProductId productId = new ProductId(1L);
        Option option = Option.fake(productId);

        assertDoesNotThrow(() -> option.checkIsValid(Size.XL, "Black"));
    }

    @Test
    void whenCheckIsValidNotPass() {
        ProductId productId = new ProductId(1L);
        Option option = Option.fake(productId);

        assertThrows(InvalidProductOption.class,
                () -> option.checkIsValid(Size.XL, "Invalid"));
    }
}
