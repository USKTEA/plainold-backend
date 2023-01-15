package com.usktea.plainold.models.order;

import com.usktea.plainold.models.common.ItemOption;
import com.usktea.plainold.models.option.Size;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class ItemOptionTest {
    @Test
    void equality() {
        ItemOption itemOption1 = new ItemOption("XL", "red");
        ItemOption itemOption2 = new ItemOption("XL", "red");
        ItemOption itemOption3 = new ItemOption("M", "red");

        assertThat(itemOption1).isEqualTo(itemOption2);
        assertThat(itemOption1).isNotEqualTo(itemOption3);
    }

    @Test
    void whenThereIsNoOptionData() {
        ItemOption itemOption = new ItemOption(null);

        assertThat(itemOption.getColor()).isEqualTo("");
        assertThat(itemOption.getSize()).isEqualTo(Size.FREE);
    }
}
