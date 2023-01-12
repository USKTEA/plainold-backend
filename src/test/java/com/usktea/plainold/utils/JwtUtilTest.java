package com.usktea.plainold.utils;

import com.usktea.plainold.models.user.Username;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class JwtUtilTest {
    @Test
    void encodeAndDecode() {
        JwtUtil jwtUtil = new JwtUtil("SECRET");

        Username username = new Username("tjrxo1234@gmail.com");

        String token = jwtUtil.encode(username.value());

        assertThat(token).contains(".");
        assertThat(jwtUtil.decode(token)).isEqualTo(username.value());
    }
}
