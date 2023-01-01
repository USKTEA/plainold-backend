package com.usktea.plainold.models;

import com.usktea.plainold.exceptions.IncorrectZipCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ZipCodeTest {

    @Test
    void creation() {
        assertDoesNotThrow(() -> new ZipCode("111111"));
    }

    @Test
    void whenInputIsBlank() {
        assertThrows(IncorrectZipCode.class, () -> new ZipCode(""));
    }

    @Test
    void whenInputIsIncorrect() {
        assertThrows(IncorrectZipCode.class, () -> new ZipCode("incorrect"));
    }

    @Test
    void equality() {
        ZipCode zipCode1 = new ZipCode("111111");
        ZipCode zipCode2 = new ZipCode("111111");
        ZipCode zipCode3 = new ZipCode("222222");

        assertThat(zipCode1).isEqualTo(zipCode2);
        assertThat(zipCode1).isNotEqualTo(zipCode3);
    }
}
