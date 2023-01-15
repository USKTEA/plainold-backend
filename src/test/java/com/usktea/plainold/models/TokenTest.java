package com.usktea.plainold.models;

import com.usktea.plainold.models.token.Token;
import com.usktea.plainold.models.user.Username;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class TokenTest {

    @Test
    void equality() {
        Username username = new Username("tjrxo1234@gmail.com");

        Token token1 = new Token(username, "a.a.a");
        Token token2 = new Token(username, "b.b.b");

        assertThat(token1).isEqualTo(token2);
    }
}
