package com.usktea.plainold.models.review;

import com.usktea.plainold.models.user.Nickname;
import com.usktea.plainold.models.user.Username;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class ReviewerTest {
    @Test
    void Equality() {
        Username username = new Username("tjrxo1234@gmail.com");
        Nickname nickname = new Nickname("김뚜루");

        Reviewer reviewer1 = new Reviewer(username, nickname);
        Reviewer reviewer2 = new Reviewer(username, nickname);

        assertThat(reviewer1).isEqualTo(reviewer2);
    }
}
