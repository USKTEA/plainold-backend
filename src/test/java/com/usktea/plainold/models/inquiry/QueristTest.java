package com.usktea.plainold.models.inquiry;

import com.usktea.plainold.models.user.Username;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class QueristTest {
    @Test
    void equality() {
        Username username1 = new Username("tjrxo1234@gmail.com");
        Username username2 = new Username("rlatjrxo1234@gmail.com");

        Querist querist1 = Querist.fake(username1);
        Querist querist2 = Querist.fake(username1);
        Querist querist3 = Querist.fake(username2);

        assertThat(querist1).isEqualTo(querist2);
        assertThat(querist1).isNotEqualTo(querist3);
    }
}
