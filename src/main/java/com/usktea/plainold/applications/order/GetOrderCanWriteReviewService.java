package com.usktea.plainold.applications.order;

import com.usktea.plainold.applications.product.GetProductService;
import com.usktea.plainold.applications.user.GetUserService;
import com.usktea.plainold.exceptions.OrderCanWriteReviewNotFound;
import com.usktea.plainold.models.order.Order;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.order.OrderStatus;
import com.usktea.plainold.models.product.Product;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.review.Review;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.repositories.OrderRepository;
import com.usktea.plainold.repositories.ReviewRepository;
import com.usktea.plainold.specifications.OrderSpecification;
import com.usktea.plainold.specifications.ReviewSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@SuppressWarnings("unchecked")
public class GetOrderCanWriteReviewService {
    private final GetUserService getUserService;
    private final GetProductService getProductService;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    public GetOrderCanWriteReviewService(GetUserService getUserService,
                                         GetProductService getProductService,
                                         OrderRepository orderRepository,
                                         ReviewRepository reviewRepository) {
        this.getUserService = getUserService;
        this.getProductService = getProductService;
        this.orderRepository = orderRepository;
        this.reviewRepository = reviewRepository;
    }

    public Order order(Username username, ProductId productId) {
        Users user = getUserService.find(username);
        Product product = getProductService.find(productId);

        List<Order> orders = getOrders(user.username());
        List<Review> reviews = getReviews(orders, product.id());

        checkHasOrders(orders);

        Order canWriteReview = filterAlreadyWrote(reviews, orders);

        return canWriteReview;
    }

    private Order filterAlreadyWrote(List<Review> reviews, List<Order> orders) {
        if (reviews.size() == orders.size()) {
            throw new OrderCanWriteReviewNotFound();
        }

        List<OrderNumber> orderNumbers = reviews.stream()
                .map(Review::orderNumber)
                .collect(Collectors.toList());

        return orders.stream()
                .filter((order -> order.checkHasSameOrder(orderNumbers)))
                .collect(Collectors.toList())
                .stream()
                .findFirst()
                .orElseThrow(OrderCanWriteReviewNotFound::new);
    }

    private void checkHasOrders(List<Order> orders) {
        if (orders.size() == 0) {
            throw new OrderCanWriteReviewNotFound();
        }
    }

    private List<Review> getReviews(List<Order> orders, ProductId productId) {
        List<OrderNumber> orderNumbers = orders.stream()
                .map(Order::orderNumber)
                .collect(Collectors.toList());

        Specification<Review> specification = Specification.where(
                ReviewSpecification.equal(orderNumbers, productId));

        return reviewRepository.findAll(specification);
    }

    private List<Order> getOrders(Username username) {
        Specification<Order> specification = Specification.where(
                OrderSpecification.equal(username, OrderStatus.DELIVERY_COMPLETED));

        return orderRepository.findAll(specification);
    }
}
