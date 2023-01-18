package com.usktea.plainold.applications;

import com.usktea.plainold.dtos.CreateReviewRequest;
import com.usktea.plainold.exceptions.OrderNotFound;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.exceptions.ReviewAlreadyWritten;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.review.Review;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
class CreateReviewServiceTest {
    private GetUserService getUserService;
    private GetProductService getProductService;
    private GetOrderService getOrderService;
    private ReviewRepository reviewRepository;
    private CreateReviewService createReviewService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        getProductService = mock(GetProductService.class);
        getOrderService = mock(GetOrderService.class);
        reviewRepository = mock(ReviewRepository.class);
        createReviewService = new CreateReviewService(
                getUserService, getProductService, getOrderService, reviewRepository);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-202301061131");

        CreateReviewRequest createReviewRequest = CreateReviewRequest.fake(orderNumber);

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class, () -> createReviewService.create(username, createReviewRequest));
    }

    @Test
    void whenProductNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-202301061131");
        ProductId productId = new ProductId(9_999_999L);
        CreateReviewRequest createReviewRequest = CreateReviewRequest.fake(productId);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getProductService.find(productId)).willThrow(ProductNotFound.class);

        assertThrows(ProductNotFound.class, () -> createReviewService.create(username, createReviewRequest));
    }

    @Test
    void whenOrderNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("notExists");
        ProductId productId = new ProductId(1L);
        CreateReviewRequest createReviewRequest = CreateReviewRequest.fake(orderNumber);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getProductService.find(productId)).willReturn(Product.fake(productId));
        given(getOrderService.find(orderNumber)).willThrow(OrderNotFound.class);

        assertThrows(OrderNotFound.class, () -> createReviewService.create(username, createReviewRequest));
    }

    @Test
    void whenReviewAlreadyExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-202301061131");
        ProductId productId = new ProductId(1L);
        CreateReviewRequest createReviewRequest = CreateReviewRequest.fake(orderNumber);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getProductService.find(productId)).willReturn(Product.fake(productId));
        given(getOrderService.find(orderNumber)).willReturn(Order.fake(orderNumber));
        given(reviewRepository.findAll(any(Specification.class)))
                .willReturn(List.of(Review.fake(productId)));

        assertThrows(ReviewAlreadyWritten.class, () -> createReviewService.create(username, createReviewRequest));
    }

    @Test
    void whenCreateReviewSuccess() {
        Username username = new Username("tjrxo1234@gmail.com");
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-202301061131");
        ProductId productId = new ProductId(1L);
        CreateReviewRequest createReviewRequest = CreateReviewRequest.fake(orderNumber);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getProductService.find(productId)).willReturn(Product.fake(productId));
        given(getOrderService.find(orderNumber)).willReturn(Order.fake(orderNumber));
        given(reviewRepository.findAll(any(Specification.class)))
                .willReturn(List.of());

        Review review = Review.fake(productId);

        given(reviewRepository.save(any())).willReturn(review);

        Review saved = createReviewService.create(username, createReviewRequest);

        assertThat(saved).isNotNull();
        verify(reviewRepository).save(any());
    }
}
