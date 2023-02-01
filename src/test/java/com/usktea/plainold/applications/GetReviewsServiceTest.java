package com.usktea.plainold.applications;

import com.usktea.plainold.applications.product.GetProductService;
import com.usktea.plainold.applications.review.GetReviewsService;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.review.Review;
import com.usktea.plainold.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class GetReviewsServiceTest {
    private GetProductService getProductService;
    private GetReviewsService getReviewsService;
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setup() {
        getProductService = mock(GetProductService.class);
        reviewRepository = mock(ReviewRepository.class);
        getReviewsService = new GetReviewsService(getProductService, reviewRepository);
    }

    @Test
    void whenProductExists() {
        ProductId productId = new ProductId(1L);
        Integer pageNumber = 1;
        Boolean photoReviews = true;
        Review review = Review.fake(productId);

        given(getProductService.find(productId))
                .willReturn(Product.fake(productId));


        Page<Review> page = new PageImpl<>(List.of(Review.fake(productId)));

        given(reviewRepository.findAll(any(Specification.class), any(Pageable.class)))
                .willReturn(page);

        Page<Review> reviews = getReviewsService.getReviews(productId, photoReviews, pageNumber);

        assertThat(reviews).hasSize(1);
        assertThat(reviews).contains(review);
    }

    @Test
    void whenProductNotExists() {
        ProductId productId = new ProductId(1L);
        Integer pageNumber = 1;
        Boolean photoReviews = true;

        given(getProductService.find(productId)).willThrow(ProductNotFound.class);

        assertThrows(ProductNotFound.class, () -> getReviewsService.getReviews(productId, photoReviews, pageNumber));
    }
}
