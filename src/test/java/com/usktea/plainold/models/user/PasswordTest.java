package com.usktea.plainold.models.user;

import com.usktea.plainold.exceptions.InvalidPassword;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class PasswordTest {
    @Test
    void creation() {
        assertDoesNotThrow(() -> new Password("Password1234!"));
    }

    @Test
    void whenInputIsInvalid() {
        assertThrows(InvalidPassword.class, () -> new Password("xxx"));
    }

    @Test
    void equality() {
        Password password1 = new Password("Password1234!");
        Password password2 = new Password("Password1234!");
        Password password3 = new Password("notPassword1234!");

        assertThat(password1).isEqualTo(password2);
        assertThat(password1).isNotEqualTo(password3);
    }
}
