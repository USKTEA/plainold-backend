package com.usktea.plainold.models.review;

import com.usktea.plainold.exceptions.ReviewerNotMatch;
import com.usktea.plainold.models.common.Comment;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Username;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
class ReviewTest {
    @Test
    void equality() {
        ProductId productId = new ProductId(1L);

        Review review1 = Review.fake(productId);
        Review review2 = Review.fake(productId);

        assertThat(review1).isEqualTo(review2);
    }

    @Test
    void checkUserIsItsReviewer() {
        Username username = new Username("tjrxo1234@gamil.com");
        Username otherUsername = new Username("notTjrxo1234@gmail.com");

        Review review = Review.fake(username);

        assertThrows(ReviewerNotMatch.class,
                () -> review.checkUserIsItsReviewer(otherUsername));
        assertDoesNotThrow(() -> review.checkUserIsItsReviewer(username));
    }

    @Test
    void modify() {
        Username username = new Username("tjrxo1234@gmail.com");
        Review review = Review.fake(username);

        Rate newRate = new Rate(1);
        Comment newComment = new Comment("새로운 내용");
        ImageUrl imageUrl = new ImageUrl();

        review.modify(newRate, newComment, imageUrl);

        assertThat(review.comment()).isEqualTo(newComment);
        assertThat(review.rate()).isEqualTo(newRate);
        assertThat(review.imageUrl()).isEqualTo(imageUrl);
    }
}
