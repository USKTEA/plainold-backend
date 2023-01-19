package com.usktea.plainold.applications;

import com.usktea.plainold.dtos.EditReviewRequest;
import com.usktea.plainold.exceptions.ReviewNotFound;
import com.usktea.plainold.exceptions.ReviewerNotMatch;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.review.Review;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
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
class EditReviewServiceTest {
    private GetUserService getUserService;
    private ReviewRepository reviewRepository;
    private EditReviewService editReviewService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        reviewRepository = mock(ReviewRepository.class);
        editReviewService = new EditReviewService(getUserService, reviewRepository);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gamil.com");
        Long reviewId = 1L;
        EditReviewRequest editReviewRequest = EditReviewRequest.fake(reviewId);

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class,
                () -> editReviewService.edit(username, editReviewRequest));
    }

    @Test
    void whenReviewNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long reviewId = 1L;
        EditReviewRequest editReviewRequest = EditReviewRequest.fake(reviewId);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(reviewRepository.findById(reviewId)).willReturn(Optional.empty());

        assertThrows(ReviewNotFound.class,
                () -> editReviewService.edit(username, editReviewRequest));
    }

    @Test
    void whenUsernameNotMatch() {
        Username username = new Username("tjrxo1234@gmail.com");
        Username otherUsername = new Username("notTjrxo1234@gmail.com");
        Long reviewId = 1L;
        EditReviewRequest editReviewRequest = EditReviewRequest.fake(reviewId);

        given(getUserService.find(otherUsername)).willReturn(Users.fake(otherUsername));

        given(reviewRepository.findById(reviewId))
                .willReturn(Optional.of(Review.fake(username)));

        assertThrows(ReviewerNotMatch.class,
                ()-> editReviewService.edit(otherUsername, editReviewRequest));
    }

    @Test
    void editReviewSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long reviewId = 1L;
        EditReviewRequest editReviewRequest = EditReviewRequest.fake(reviewId);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(reviewRepository.findById(reviewId))
                .willReturn(Optional.of(Review.fake(username)));

        Review edited = editReviewService.edit(username, editReviewRequest);

        assertThat(edited.comment()).isEqualTo(editReviewRequest.comment());
        assertThat(edited.rate()).isEqualTo(editReviewRequest.rate());
    }
}
