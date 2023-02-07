package com.usktea.plainold.models.review;

import com.usktea.plainold.dtos.EditReviewRequest;
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
    void modify() {
        Username username = new Username("tjrxo1234@gmail.com");
        Review review = Review.fake(username);

        Rate newRate = new Rate(5);
        Comment newComment = new Comment("아주 좋은 상품");
        ImageUrl imageUrl = new ImageUrl("1");

        EditReviewRequest editReviewRequest = EditReviewRequest.fake(1L);

        review.edit(username, editReviewRequest);

        assertThat(review.comment()).isEqualTo(newComment);
        assertThat(review.rate()).isEqualTo(newRate);
        assertThat(review.imageUrl()).isEqualTo(imageUrl);
    }
}
