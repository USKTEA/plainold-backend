package com.usktea.plainold.models.review;

import com.usktea.plainold.dtos.EditReviewRequest;
import com.usktea.plainold.dtos.ReviewDto;
import com.usktea.plainold.exceptions.ReviewerNotMatch;
import com.usktea.plainold.models.common.Comment;
import com.usktea.plainold.models.order.OrderNumber;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.user.Role;
import com.usktea.plainold.models.user.Username;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    private ImageUrl imageUrl;

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
                  ImageUrl imageUrl,
                  LocalDateTime createdAt) {
        this.id = id;
        this.productId = productId;
        this.orderNumber = orderNumber;
        this.reviewer = reviewer;
        this.rate = rate;
        this.comment = comment;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public Review(ProductId productId,
                  OrderNumber orderNumber,
                  Reviewer reviewer,
                  Rate rate,
                  Comment comment,
                  ImageUrl imageUrl
    ) {
        this.productId = productId;
        this.orderNumber = orderNumber;
        this.reviewer = reviewer;
        this.rate = rate;
        this.comment = comment;
        this.imageUrl = imageUrl;
    }

    public static Review fake(ProductId productId) {
        return new Review(
                1L,
                productId,
                new OrderNumber("tjrxo1234-202301061131"),
                Reviewer.fake("김뚜루"),
                new Rate(5),
                new Comment("좋은 상품입니다"),
                new ImageUrl("1"),
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
                new ImageUrl("1"),
                LocalDateTime.now()
        );
    }

    public static Review fake(Username username) {
        return new Review(
                1L,
                new ProductId(1L),
                new OrderNumber("tjrxo1234-202301061131"),
                Reviewer.fake(username),
                new Rate(5),
                new Comment("좋은 상품입니다"),
                new ImageUrl("1"),
                LocalDateTime.now()
        );
    }

    public static Review fake(Long reviewId) {
        return new Review(
                reviewId,
                new ProductId(1L),
                new OrderNumber("tjrxo1234-202301061131"),
                Reviewer.fake("tjrxo1234@gmail.com"),
                new Rate(5),
                new Comment("좋은 상품입니다"),
                new ImageUrl("1"),
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
        if (Objects.isNull(imageUrl)) {
            return new ReviewDto(
                    id,
                    productId.value(),
                    reviewer.toDto(),
                    rate.getValue(),
                    comment.getValue(),
                    format(createdAt)
            );
        }

        return new ReviewDto(
                id,
                productId.value(),
                reviewer.toDto(),
                rate.getValue(),
                comment.getValue(),
                imageUrl.getValue(),
                format(createdAt));
    }

    public void edit(Username username, EditReviewRequest editReviewRequest) {
        if (!Objects.equals(reviewer.getUsername(), username)) {
            throw new ReviewerNotMatch();
        }

        this.rate = editReviewRequest.rate();
        this.comment = editReviewRequest.comment();
        this.imageUrl = editReviewRequest.imageUrl();
    }

    public void checkUserAuthority(Username username, Role role) {
        if (role.isAdmin()) {
            return;
        }

        if (!Objects.equals(reviewer.getUsername(), username)) {
            throw new ReviewerNotMatch();
        }
    }

    private String format(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ZonedDateTime koreaZonedDateTime = time.atZone(ZoneId.of("Asia/Seoul"));

        return formatter.format(koreaZonedDateTime);
    }

    public Long id() {
        return id;
    }

    public OrderNumber orderNumber() {
        return orderNumber;
    }

    public Comment comment() {
        return comment;
    }

    public Rate rate() {
        return rate;
    }

    public ImageUrl imageUrl() {
        return imageUrl;
    }
}
