package com.usktea.plainold.models;

import com.usktea.plainold.exceptions.IncorrectAddress1;
import com.usktea.plainold.models.order.Address1;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
class Address1Test {

    @Test
    void creation() {
        assertDoesNotThrow(() -> new Address1("서울시 성동구 상원12길 34"));
    }

    @Test
    void whenInputValueIsBlank() {
        assertThrows(IncorrectAddress1.class, () -> new Address1(""));
    }

    @Test
    void whenInputIsInvalid() {
        assertThrows(IncorrectAddress1.class, () -> new Address1("잘못된주소"));
    }

    @Test
    void equality() {
        Address1 address1 = new Address1("서울시 성동구 상원12길 34");
        Address1 address2 = new Address1("서울시 성동구 상원12길 34");
        Address1 address3 = new Address1("서울시 성동구 상원12길 35");

        assertThat(address1).isEqualTo(address2);
        assertThat(address1).isNotEqualTo(address3);
    }
}
