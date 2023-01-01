package com.usktea.plainold.models;

import com.usktea.plainold.exceptions.IncorrectPhoneNumber;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PhoneNumberTest {
    @Test
    void creation() {
        assertDoesNotThrow(() -> new PhoneNumber("010-2222-2222"));
    }

    @Test
    void whenInputValueIsBlank() {
        assertThrows(IncorrectPhoneNumber.class, () -> new PhoneNumber(""));
    }

    @Test
    void whenInputIsInvalid() {
        assertThrows(IncorrectPhoneNumber.class, () -> new PhoneNumber("010-22aa-aaaa"));
    }

    @Test
    void equality() {
        PhoneNumber phoneNumber1 = new PhoneNumber("010-2222-2222");
        PhoneNumber phoneNumber2 = new PhoneNumber("010-2222-2222");
        PhoneNumber phoneNumber3 = new PhoneNumber("010-1111-1111");

        assertThat(phoneNumber1).isEqualTo(phoneNumber2);
        assertThat(phoneNumber1).isNotEqualTo(phoneNumber3);
    }
}
