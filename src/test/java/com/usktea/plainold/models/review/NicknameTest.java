package com.usktea.plainold.models.review;

import com.usktea.plainold.models.user.Nickname;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class NicknameTest {
    @Test
    void equality() {
        Nickname nickname1 = new Nickname("김뚜루");
        Nickname nickname2 = new Nickname("김뚜루");
        Nickname nickname3 = new Nickname("안김뚜루");

        assertThat(nickname1).isEqualTo(nickname2);
        assertThat(nickname1).isNotEqualTo(nickname3);
    }
}
