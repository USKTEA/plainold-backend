package com.usktea.plainold.models.review;

import com.usktea.plainold.models.common.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class CommentTest {

    @Test
    void equality() {
        Comment wow = new Comment("wow");
        Comment otherWow = new Comment("wow");
        Comment mom = new Comment("mom");

        assertThat(wow).isEqualTo(otherWow);
        assertThat(wow).isNotEqualTo(mom);
    }
}
