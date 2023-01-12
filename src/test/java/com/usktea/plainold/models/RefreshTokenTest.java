package com.usktea.plainold.models;

import com.usktea.plainold.models.user.Username;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class RefreshTokenTest {

    @Test
    void equality() {
        Username username = new Username("tjrxo1234@gmail.com");

        RefreshToken refreshToken1 = new RefreshToken(username, "a.a.a");
        RefreshToken refreshToken2 = new RefreshToken(username, "b.b.b");

        assertThat(refreshToken1).isEqualTo(refreshToken2);
    }
}
