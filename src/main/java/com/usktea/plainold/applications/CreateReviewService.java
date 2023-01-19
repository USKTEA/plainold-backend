package com.usktea.plainold.applications;

import com.usktea.plainold.dtos.CreateReviewRequest;
import com.usktea.plainold.exceptions.ReviewAlreadyWritten;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.review.Review;
import com.usktea.plainold.models.review.Reviewer;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.ReviewRepository;
import com.usktea.plainold.specifications.ReviewSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class CreateReviewService {
    private final GetUserService getUserService;
    private final GetProductService getProductService;
    private final GetOrderService getOrderService;
    private final ReviewRepository reviewRepository;

    public CreateReviewService(GetUserService getUserService,
                               GetProductService getProductService,
                               GetOrderService getOrderService,
                               ReviewRepository reviewRepository) {
        this.getUserService = getUserService;
        this.getProductService = getProductService;
        this.getOrderService = getOrderService;
        this.reviewRepository = reviewRepository;
    }

    public Review create(Username username, CreateReviewRequest createReviewRequest) {
        ProductId productId = createReviewRequest.getProductId();
        OrderNumber orderNumber = createReviewRequest.getOrderNumber();

        Users user = getUserService.find(username);
        Product product = getProductService.find(productId);
        Order order = getOrderService.find(orderNumber);

        List<Review> reviews = getReview(orderNumber, productId);

        if (reviews.size() != 0) {
            throw new ReviewAlreadyWritten();
        }

        // 이미지 추가하는 작업해야함
        Review review = new Review(
                product.id(),
                order.orderNumber(),
                new Reviewer(username, user.nickname()),
                createReviewRequest.rate(),
                createReviewRequest.comment()
        );

        Review saved = reviewRepository.save(review);

        return saved;
    }

    private List<Review> getReview(OrderNumber orderNumber, ProductId productId) {
        Specification<Review> specification = Specification.where(
                ReviewSpecification.equal(orderNumber, productId));

        return reviewRepository.findAll(specification);
    }
}

