package com.usktea.plainold.dtos;

import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.review.Comment;
import com.usktea.plainold.models.review.Rate;

public class CreateReviewRequest {
    private OrderNumber orderNumber;
    private ProductId productId;
    private Rate rate;
    private Comment comment;

    public CreateReviewRequest(OrderNumber orderNumber,
                               ProductId productId,
                               Rate rate,
                               Comment comment) {
        this.orderNumber = orderNumber;
        this.productId = productId;
        this.rate = rate;
        this.comment = comment;
    }

    public static CreateReviewRequest of(CreateReviewRequestDto createReviewRequestDto) {
        return new CreateReviewRequest(
                new OrderNumber(createReviewRequestDto.getOrderNumber()),
                new ProductId(createReviewRequestDto.getProductId()),
                new Rate(createReviewRequestDto.getRate()),
                new Comment(createReviewRequestDto.getComment())
        );
    }

    public static CreateReviewRequest fake(OrderNumber orderNumber) {
        return new CreateReviewRequest(
                orderNumber,
                new ProductId(1L),
                new Rate(5),
                new Comment("좋은 상품입니다")
        );
    }

    public static CreateReviewRequest fake(ProductId productId) {
        return new CreateReviewRequest(
                new OrderNumber("tjrxo1234-202301061131"),
                productId,
                new Rate(5),
                new Comment("좋은 상품입니다")
        );
    }

    public ProductId getProductId() {
        return productId;
    }

    public OrderNumber getOrderNumber() {
        return orderNumber;
    }

    public Rate rate() {
        return rate;
    }

    public Comment comment() {
        return comment;
    }
}