package com.usktea.plainold.models.answer;

import com.usktea.plainold.models.user.Username;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles
class AnswererTest {
    @Test
    void equality() {
        Username username = new Username("tjrxo1234@gmail.com");
        Username otherUsername = new Username("otherUser@gmail.com");

        Answerer answerer1 = Answerer.fake(username);
        Answerer answerer2 = Answerer.fake(username);
        Answerer answerer3 = Answerer.fake(otherUsername);

        assertThat(answerer1).isEqualTo(answerer2);
        assertThat(answerer1).isNotEqualTo(answerer3);
    }
}
