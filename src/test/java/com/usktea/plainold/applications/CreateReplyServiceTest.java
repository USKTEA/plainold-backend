package com.usktea.plainold.applications;

import com.usktea.plainold.applications.reply.CreateReplyService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.dtos.CreateReplyRequest;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
class CreateReplyServiceTest {
    private GetUserService getUserService;
    private CreateReplyService createReplyService;
    private ReviewRepository reviewRepository;
    private ReplyRepository replyRepository;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        reviewRepository = mock(ReviewRepository.class);
        replyRepository = mock(ReplyRepository.class);

        createReplyService = new CreateReplyService(
                getUserService, reviewRepository, replyRepository);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long reviewId = 1L;
        CreateReplyRequest createReplyRequest = CreateReplyRequest.fake(reviewId);

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class,
                () -> createReplyService.create(username, createReplyRequest));
    }

    @Test
    void whenReviewNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long reviewId = 9_999_999L;
        CreateReplyRequest createReplyRequest = CreateReplyRequest.fake(reviewId);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(reviewRepository.findById(reviewId)).willReturn(Optional.empty());

        assertThrows(ReviewNotFound.class,
                () -> createReplyService.create(username, createReplyRequest));
    }

    @Test
    void whenCreateSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long reviewId = 1L;
        CreateReplyRequest createReplyRequest = CreateReplyRequest.fake(reviewId);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(reviewRepository.findById(reviewId))
                .willReturn(Optional.of(Review.fake(reviewId)));
        given(replyRepository.save(any())).willReturn(Reply.fake(1L));

        Long id = createReplyService.create(username, createReplyRequest);

        assertThat(id).isNotNull();

        verify(replyRepository).save(any());
    }
}
