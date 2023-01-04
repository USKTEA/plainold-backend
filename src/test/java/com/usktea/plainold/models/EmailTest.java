package com.usktea.plainold.models;

import com.usktea.plainold.exceptions.IncorrectEmail;
import com.usktea.plainold.models.order.Email;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
class EmailTest {
    @Test
    void creation() {
        assertDoesNotThrow(() -> new Email("tjrxo1234@gmail.com"));
    }

    @Test
    void whenInputIsBlank() {
        assertThrows(IncorrectEmail.class, () -> new Email(""));
    }

    @Test
    void whenInputIsInvalid() {
        assertThrows(IncorrectEmail.class, () -> new Email("incorrectEmail"));
    }

    @Test
    void equality() {
        Email email1 = new Email("tjrxo1234@gmail.com");
        Email email2 = new Email("tjrxo1234@gmail.com");
        Email email3 = new Email("rlatjrxo1234@gmail.com");

        assertThat(email1).isEqualTo(email2);
        assertThat(email1).isNotEqualTo(email3);
    }
}
