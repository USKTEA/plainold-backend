package com.usktea.plainold.models.order;

import com.usktea.plainold.exceptions.IncorrectAddress2;
import com.usktea.plainold.models.order.Address2;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
class Address2Test {

    @Test
    void creation() {
        assertDoesNotThrow(() -> new Address2("비비큐치킨빌딩"));
    }

    @Test
    void whenInputIsBlank() {
        assertThrows(IncorrectAddress2.class, () -> new Address2(""));
    }

    @Test
    void equality() {
        Address2 address1 = new Address2("에이원지식산업센터");
        Address2 address2 = new Address2("에이원지식산업센터");
        Address2 address3 = new Address2("교촌치킨빌딩");

        assertThat(address1).isEqualTo(address2);
        assertThat(address1).isNotEqualTo(address3);
    }
}
