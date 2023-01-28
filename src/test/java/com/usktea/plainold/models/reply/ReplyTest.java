package com.usktea.plainold.models.reply;

import com.usktea.plainold.exceptions.ReplierNotMatch;
import com.usktea.plainold.models.common.Comment;
import com.usktea.plainold.models.user.Username;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
class ReplyTest {
    @Test
    void whenEditSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        Comment comment = new Comment("수정된 커멘트");
        Reply reply = Reply.fake(username);

        reply.edit(username, comment);

        assertThat(reply.comment()).isEqualTo(comment);
    }

    @Test
    void whenEditFail() {
        Username username = new Username("tjrxo1234@gmail.com");
        Username otherUser = new Username("notTjrxo1234@gmail.com");
        Comment comment = new Comment("수정된 커멘트");
        Reply reply = Reply.fake(username);

        assertThrows(ReplierNotMatch.class, () -> reply.edit(otherUser, comment));
    }

    @Test
    void whenDeleteSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");

        Reply reply = Reply.fake(username);

        reply.delete(username);

        assertThat(reply.status()).isEqualTo(Status.DELETED);
    }

    @Test
    void whenDeleteFail() {
        Username username = new Username("tjrxo1234@gmail.com");
        Username otherUsername = new Username("otherUser@gmail.com");

        Reply reply = Reply.fake(username);

        assertThrows(ReplierNotMatch.class, () -> reply.delete(otherUsername));
    }
}
