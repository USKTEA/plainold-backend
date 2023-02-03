package com.usktea.plainold.models.reply;

import com.usktea.plainold.models.user.Nickname;
import com.usktea.plainold.models.user.Username;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class ReplierTest {

    @Test
    void equality() {
        Username username = new Username("tjrxo1234@gmail.com");
        Nickname nickname = new Nickname("김뚜루");

        Replier replier1 = new Replier(username, nickname);
        Replier replier2 = new Replier(username, nickname);

        assertThat(replier1).isEqualTo(replier2);
    }
}
