package com.usktea.plainold.models.user;

import com.usktea.plainold.exceptions.InvalidUsername;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class UsernameTest {
    @Test
    void creation() {
        assertDoesNotThrow(() -> new Username("tjrxo1234@gmail.com"));
    }

    @Test
    void whenInputIsInvalid() {
        assertThrows(InvalidUsername.class, () -> new Username("xxx"));
    }

    @Test
    void equality() {
        Username username1 = new Username("tjrxo1234@gmail.com");
        Username username2 = new Username("tjrxo1234@gmail.com");
        Username username3 = new Username("notTjrxo1234@gmail.com");

        assertThat(username1).isEqualTo(username2);
        assertThat(username1).isNotEqualTo(username3);
    }
}
