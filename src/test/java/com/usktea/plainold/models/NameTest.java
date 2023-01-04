package com.usktea.plainold.models;

import com.usktea.plainold.exceptions.IncorrectName;
import com.usktea.plainold.models.common.Name;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
class NameTest {
    @Test
    void creation() {
        assertDoesNotThrow(() -> new Name("김뚜루"));
    }

    @Test
    void whenInputIsBlank() {
        assertThrows(IncorrectName.class, () -> new Name(""));
    }

    @Test
    void whenInputsLessThanTwoCharacters() {
        assertThrows(IncorrectName.class, () -> new Name("김"));
    }

    @Test
    void equality() {
        Name name1 = new Name("김뚜루");
        Name name2 = new Name("김뚜루");
        Name name3 = new Name("안김뚜루");

        assertThat(name1).isEqualTo(name2);
        assertThat(name2).isNotEqualTo(name3);
    }
}
