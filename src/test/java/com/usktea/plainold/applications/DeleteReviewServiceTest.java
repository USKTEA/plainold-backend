package com.usktea.plainold.applications;

import com.usktea.plainold.applications.review.DeleteReviewService;
import com.usktea.plainold.applications.user.GetUserService;
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
class DeleteReviewServiceTest {
    private GetUserService getUserService;
    private ReviewRepository reviewRepository;
    private DeleteReviewService deleteReviewService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        reviewRepository = mock(ReviewRepository.class);
        deleteReviewService = new DeleteReviewService(getUserService, reviewRepository);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gmail.com");
        Long id = 1L;

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class,
                () -> deleteReviewService.delete(username, id));
    }

    @Test
    void whenReviewNotExists() {
        Username username = new Username("tjrxo1234@gamil.com");
        Long id = 9_999_999L;

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(reviewRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(ReviewNotFound.class, () -> deleteReviewService.delete(username, id));
    }

    @Test
    void whenUsernameNotMatch() {
        Username username = new Username("tjrxo1234@gmail.com");
        Username otherUsername = new Username("someoneOther@gmail.com");
        Long id = 1L;

        given(getUserService.find(otherUsername)).willReturn(Users.fake(otherUsername));
        given(reviewRepository.findById(id))
                .willReturn(Optional.of(Review.fake(username)));

        assertThrows(ReviewerNotMatch.class, () -> deleteReviewService.delete(otherUsername, id));
    }

    @Test
    void whenDeleteSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        Long id = 1L;

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(reviewRepository.findById(id))
                .willReturn(Optional.of(Review.fake(username)));

        Review deleted = deleteReviewService.delete(username, id);

        assertThat(deleted.id()).isEqualTo(id);
    }
}
