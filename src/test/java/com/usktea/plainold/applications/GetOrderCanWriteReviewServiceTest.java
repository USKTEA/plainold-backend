package com.usktea.plainold.applications;

import com.usktea.plainold.exceptions.OrderCanWriteReviewNotFound;
import com.usktea.plainold.exceptions.ProductNotFound;
import com.usktea.plainold.exceptions.UserNotExists;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.review.Review;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.OrderRepository;
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

@ActiveProfiles("test")
class GetOrderCanWriteReviewServiceTest {
    private GetUserService getUserService;
    private GetProductService getProductService;
    private OrderRepository orderRepository;
    private ReviewRepository reviewRepository;
    private GetOrderCanWriteReviewService getOrderCanWriteReviewService;

    @BeforeEach
    void setup() {
        getUserService = mock(GetUserService.class);
        getProductService = mock(GetProductService.class);
        orderRepository = mock(OrderRepository.class);
        reviewRepository = mock(ReviewRepository.class);
        getOrderCanWriteReviewService = new GetOrderCanWriteReviewService(
                getUserService, getProductService, orderRepository, reviewRepository);
    }

    @Test
    void whenUserNotExists() {
        Username username = new Username("notExists@gmail.com");
        ProductId productId = new ProductId(1L);

        given(getUserService.find(username)).willThrow(UserNotExists.class);

        assertThrows(UserNotExists.class,
                () -> getOrderCanWriteReviewService.order(username, productId));
    }

    @Test
    void whenProductNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(9_999_999L);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getProductService.find(productId)).willThrow(ProductNotFound.class);

        assertThrows(ProductNotFound.class,
                () -> getOrderCanWriteReviewService.order(username, productId));
    }

    @Test
    void whenOrderNotExists() {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(1L);

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getProductService.find(productId)).willReturn(Product.fake(productId));
        given(orderRepository.findAll(any(Specification.class)))
                .willReturn(List.of());

        assertThrows(OrderCanWriteReviewNotFound.class,
                () -> getOrderCanWriteReviewService.order(username, productId));
    }

    @Test
    void whenReviewAlreadyWritten() {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(1L);
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-202301061131");

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getProductService.find(productId)).willReturn(Product.fake(productId));
        given(orderRepository.findAll(any(Specification.class)))
                .willReturn(List.of(Order.fake(orderNumber)));
        given(reviewRepository.findAll(any(Specification.class)))
                .willReturn(List.of(Review.fake(productId, orderNumber)));

        assertThrows(OrderCanWriteReviewNotFound.class,
                () -> getOrderCanWriteReviewService.order(username, productId));
    }

    @Test
    void whenFindOrderCanWriteReview() {
        Username username = new Username("tjrxo1234@gmail.com");
        ProductId productId = new ProductId(1L);
        OrderNumber orderNumber = new OrderNumber("tjrxo1234-202301061131");

        given(getUserService.find(username)).willReturn(Users.fake(username));
        given(getProductService.find(productId)).willReturn(Product.fake(productId));
        given(orderRepository.findAll(any(Specification.class)))
                .willReturn(List.of(Order.fake(orderNumber)));
        given(reviewRepository.findAll(any(Specification.class)))
                .willReturn(List.of());

        Order order = getOrderCanWriteReviewService.order(username, productId);

        assertThat(order.orderNumber()).isEqualTo(orderNumber);
    }
}
