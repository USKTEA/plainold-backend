package com.usktea.plainold.applications;

import com.usktea.plainold.applications.reply.EditReplyService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.dtos.EditReplyRequest;
import com.usktea.plainold.exceptions.ReplierNotMatch;
import com.usktea.plainold.exceptions.ReplyNotExists;
import com.usktea.plainold.exceptions.ReviewNotFound;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.reply.Reply;
import com.usktea.plainold.models.review.Review;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.ReplyRepository;
import com.usktea.plainold.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class EditReplyServiceTest {
    private GetUserService getUserService;
    private ReviewRepository reviewRepository;
    private ReplyRepository replyRepository;
    private EditReplyService editReplyService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        reviewRepository = mock(ReviewRepository.class);
        replyRepository = mock(ReplyRepository.class);
        editReplyService = new EditReplyService(
                getUserService, reviewRepository, replyRepository);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gamil.com");
        Long replyId = 1L;
        EditReplyRequest editReplyRequest = EditReplyRequest.fake(replyId);

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class,
                () -> editReplyService.edit(username, editReplyRequest));
    }

    @Test
    void whenReplierNotMatch() {
        Username otherUser = new Username("otherUser@gmail.com");
        Username replierName = new Username("tjrxo1234@gmail.com");
        Long replyId = 1L;
        Long reviewId = 1L;
        EditReplyRequest editReplyRequest = EditReplyRequest.fake(replyId, reviewId);

        given(getUserService.find(otherUser))
                .willReturn(Users.fake(otherUser));
        given(replyRepository.findById(replyId))
                .willReturn(Optional.of(Reply.fake(replierName)));
        given(reviewRepository.findById(replyId))
                .willReturn(Optional.of(Review.fake(reviewId)));

        assertThrows(ReplierNotMatch.class,
                () -> editReplyService.edit(otherUser, editReplyRequest));
    }

    @Test
    void whenReplyNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long replyId = 9_999_999L;
        Long reviewId = 1L;
        EditReplyRequest editReplyRequest = EditReplyRequest.fake(replyId, reviewId);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(replyRepository.findById(replyId)).willThrow(ReplyNotExists.class);
        given(reviewRepository.findById(replyId))
                .willReturn(Optional.of(Review.fake(reviewId)));

        assertThrows(ReplyNotExists.class,
                () -> editReplyService.edit(username, editReplyRequest));
    }

    @Test
    void whenReviewNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long replyId = 1L;
        Long reviewId = 9_999_999L;
        EditReplyRequest editReplyRequest = EditReplyRequest.fake(replyId, reviewId);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(replyRepository.findById(replyId))
                .willReturn(Optional.of(Reply.fake(replyId)));
        given(reviewRepository.findById(reviewId)).willThrow(ReviewNotFound.class);

        assertThrows(ReviewNotFound.class,
                () -> editReplyService.edit(username, editReplyRequest));
    }

    @Test
    void whenEditReviewSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");

        Long replyId = 1L;
        Long reviewId = 1L;
        EditReplyRequest editReplyRequest = EditReplyRequest.fake(replyId, reviewId);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(replyRepository.findById(replyId))
                .willReturn(Optional.of(Reply.fake(replyId)));
        given(reviewRepository.findById(replyId))
                .willReturn(Optional.of(Review.fake(reviewId)));

        Long id = editReplyService.edit(username, editReplyRequest);

        assertThat(id).isEqualTo(replyId);
    }
}
