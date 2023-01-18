package com.usktea.plainold.models.review;

import com.usktea.plainold.dtos.ReviewDto;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.product.ProductId;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private ProductId productId;

    @Embedded
    private OrderNumber orderNumber;

    @Embedded
    private Reviewer reviewer;

    @Embedded
    private Rate rate;

    @Embedded
    private Comment comment;

    @Embedded
    private ReviewImageUrl reviewImageUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Review() {
    }

    public Review(Long id,
                  ProductId productId,
                  OrderNumber orderNumber,
                  Reviewer reviewer,
                  Rate rate,
                  Comment comment,
                  ReviewImageUrl reviewImageUrl,
                  LocalDateTime createdAt) {
        this.id = id;
        this.productId = productId;
        this.orderNumber = orderNumber;
        this.reviewer = reviewer;
        this.rate = rate;
        this.comment = comment;
        this.reviewImageUrl = reviewImageUrl;
        this.createdAt = createdAt;
    }

    public Review(ProductId productId,
                  OrderNumber orderNumber,
                  Reviewer reviewer,
                  Rate rate,
                  Comment comment) {
        this.productId = productId;
        this.orderNumber = orderNumber;
        this.reviewer = reviewer;
        this.rate = rate;
        this.comment = comment;
    }

    public static Review fake(ProductId productId) {
        return new Review(
                1L,
                productId,
                new OrderNumber("tjrxo1234-202301061131"),
                Reviewer.fake("김뚜루"),
                new Rate(5),
                new Comment("좋은 상품입니다"),
                new ReviewImageUrl("1"),
                LocalDateTime.now()
        );
    }

    public static Review fake(ProductId productId, OrderNumber orderNumber) {
        return new Review(
                1L,
                productId,
                orderNumber,
                Reviewer.fake("김뚜루"),
                new Rate(5),
                new Comment("좋은 상품입니다"),
                new ReviewImageUrl("1"),
                LocalDateTime.now()
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Review otherReview = (Review) other;

        return Objects.equals(id, otherReview.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public ReviewDto toDto() {
        if (Objects.isNull(reviewImageUrl)) {
            return new ReviewDto(
                    id,
                    productId.value(),
                    reviewer.toDto(),
                    rate.getValue(),
                    comment.getValue(),
                    format(createdAt)
            );
        }

        return new ReviewDto(id,
                productId.value(),
                reviewer.toDto(),
                rate.getValue(),
                comment.getValue(),
                reviewImageUrl.getValue(),
                format(createdAt));
    }

    private String format(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ZonedDateTime koreaZonedDateTime = time.atZone(ZoneId.of("Asia/Seoul"));

        return formatter.format(koreaZonedDateTime);
    }

    public Long id() {
        return id;
    }
}
