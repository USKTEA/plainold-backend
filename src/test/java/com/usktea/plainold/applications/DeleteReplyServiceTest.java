package com.usktea.plainold.applications;

import com.usktea.plainold.exceptions.ReplierNotMatch;
import com.usktea.plainold.exceptions.ReplyNotExists;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.reply.Reply;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.ReplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
class DeleteReplyServiceTest {
    private GetUserService getUserService;
    private ReplyRepository replyRepository;
    private DeleteReplyService deleteReplyService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        replyRepository = mock(ReplyRepository.class);
        deleteReplyService = new DeleteReplyService(getUserService, replyRepository);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gmail.com");
        Long replyId = 1L;

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class, () -> deleteReplyService.delete(username, replyId));
    }

    @Test
    void whenReplyNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long replyId = 9_999_999L;

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(replyRepository.findById(replyId)).willThrow(ReplyNotExists.class);

        assertThrows(ReplyNotExists.class, () -> deleteReplyService.delete(username, replyId));
    }

    @Test
    void whenReplierNotMatch() {
        Username username = new Username("tjrxo1234@gmail.com");
        Username otherUsername = new Username("notTjrxo1234@gamil.com");
        Long replyId = 1L;

        given(getUserService.find(otherUsername)).willReturn(Users.fake(otherUsername));
        given(replyRepository.findById(replyId))
                .willReturn(Optional.of(Reply.fake(username)));

        assertThrows(ReplierNotMatch.class, () -> deleteReplyService.delete(otherUsername, replyId));
    }

    @Test
    void deleteSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long replyId = 1L;

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(replyRepository.findById(replyId)).willReturn(Optional.of(Reply.fake(replyId)));

        Long deletedId = deleteReplyService.delete(username, replyId);

        assertThat(deletedId).isEqualTo(replyId);
    }
}
